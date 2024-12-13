package com.example.Integradoraturismo.controller;


import com.example.Integradoraturismo.dto.TicketDto;
import com.example.Integradoraturismo.models.Ticket;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.TicketService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;    

    // @GetMapping
    // public ResponseEntity<List<Ticket>> getAllTickets() {
    //     return ResponseEntity.ok(ticketService.getAllTickets());
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
    //     return ticketService.getTicketById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> obtenerTicketPorId(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        TicketDto ticketDto = ticketService.convertirTicketADto(ticket);
        return ResponseEntity.ok(new ApiResponse("success", ticketDto));
    }
    
    @GetMapping("/folio/{folio}")
    public ResponseEntity<ApiResponse> obtenerTicketPorId(@PathVariable String folio) {
        Ticket ticket = ticketService.getTicketByFolio(folio);
        TicketDto ticketDto = ticketService.convertirTicketADto(ticket);
        return ResponseEntity.ok(new ApiResponse("success", ticketDto));
    }

    // @PostMapping
    // public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
    //     return ResponseEntity.ok(ticketService.saveTicket(ticket));
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
    //     return ticketService.getTicketById(id)
    //             .map(ticket -> {
    //                 ticket.setFolio(ticketDetails.getFolio());
    //                 ticket.setEstatus(ticketDetails.getEstatus());
    //                 ticket.setPedidoFechaReservacion(ticketDetails.getPedidoFechaReservacion());
    //                 return ResponseEntity.ok(ticketService.saveTicket(ticket));
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    //     if (ticketService.getTicketById(id).isPresent()) {
    //         ticketService.deleteTicket(id);
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.notFound().build();
    // }
    
    ////@PreAuthorize("hasRole('EMPRESA')")
    @PatchMapping("/id/cancelar")
    public ResponseEntity<ApiResponse> cancelarTicketPorId(@RequestParam Long id) {        
        try {
            Ticket cancelledTicket = ticketService.cancelarTicketPorId(id);
            TicketDto cancelledTicketDto = ticketService.convertirTicketADto(cancelledTicket);
            return ResponseEntity.ok(new ApiResponse("Ticket cancelado", cancelledTicketDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    ////@PreAuthorize("hasRole('EMPRESA')")
    @PatchMapping("/folio/cancelar")
    public ResponseEntity<ApiResponse> cancelarTicketPorId(@RequestParam String folio) {        
        try {
            Ticket cancelledTicket = ticketService.cancelarTicketPorFolio(folio);
            TicketDto cancelledTicketDto = ticketService.convertirTicketADto(cancelledTicket);
            return ResponseEntity.ok(new ApiResponse("Ticket cancelado", cancelledTicketDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    
        

}
