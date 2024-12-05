package com.example.Integradoraturismo.service;


import com.example.Integradoraturismo.dto.TicketDto;
import com.example.Integradoraturismo.enums.EstatusTicket;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Ticket;
import com.example.Integradoraturismo.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
    }
    
    public Ticket getTicketByFolio(String folio) {
        return ticketRepository.findByFolio(folio)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    
    public Ticket cancelarTicketPorId(Long id) {
        Ticket ticket = getTicketById(id);       
        ticket.setEstatus(EstatusTicket.CANCELADO);
        return ticketRepository.save(ticket);
    }
    
    public Ticket cancelarTicketPorFolio(String folio) {
        Ticket ticket = getTicketByFolio(folio);       
        ticket.setEstatus(EstatusTicket.CANCELADO);
        return ticketRepository.save(ticket);
    }
    
    // Convertir un ticket a DTO
    public TicketDto convertirTicketADto(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }

    // Convertir una lista de tickets a DTOs
    public List<TicketDto> convertirTodosLosTicketsADto(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .collect(Collectors.toList());
    }
}

