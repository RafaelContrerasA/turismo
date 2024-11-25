package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.PedidoFechaReservacionDto;
import com.example.Integradoraturismo.models.PedidoFechaReservacion;
import com.example.Integradoraturismo.repository.PedidoFechaReservacionRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoFechaReservacionService {

    private final PedidoFechaReservacionRepository pedidoFechaReservacionRepository;
    private final ModelMapper modelMapper;

    public PedidoFechaReservacionDto obtenerPorId(Long id) {
        PedidoFechaReservacion reservacion = pedidoFechaReservacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));
        return convertirAReservacionDto(reservacion);
    }

    public PedidoFechaReservacionDto guardarReservacion(PedidoFechaReservacionDto reservacionDto) {
        PedidoFechaReservacion reservacion = convertirAReservacionEntidad(reservacionDto);
        PedidoFechaReservacion reservacionGuardada = pedidoFechaReservacionRepository.save(reservacion);
        return convertirAReservacionDto(reservacionGuardada);
    }

    // Métodos auxiliares de mapeo
    private PedidoFechaReservacionDto convertirAReservacionDto(PedidoFechaReservacion reservacion) {
        return modelMapper.map(reservacion, PedidoFechaReservacionDto.class);
    }

    private PedidoFechaReservacion convertirAReservacionEntidad(PedidoFechaReservacionDto reservacionDto) {
        return modelMapper.map(reservacionDto, PedidoFechaReservacion.class);
    }
}