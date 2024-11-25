package com.example.Integradoraturismo.service;


import com.example.Integradoraturismo.dto.FechaReservacionDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.FechaReservacion;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.repository.FechaReservacionRepository;
import com.example.Integradoraturismo.request.FechaReservacionCreateRequest;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FechaReservacionService {

    private final FechaReservacionRepository fechaReservacionRepository;
    private final ReservacionService reservacionService;
    private final ModelMapper modelMapper;

    // Crear una nueva fecha de reservación
    public FechaReservacion crearFechaReservacion(FechaReservacionCreateRequest request) {
        FechaReservacion fechaReservacion = transformarRequestAFechaReservacion(request);
        return fechaReservacionRepository.save(fechaReservacion);
    }
    
    public FechaReservacion transformarRequestAFechaReservacion(FechaReservacionCreateRequest request){
        FechaReservacion fechaReservacion = new FechaReservacion();
        fechaReservacion.setCupoDisponible(request.getCupoDisponible());
        fechaReservacion.setCupoTotal(request.getCupoDisponible());
        fechaReservacion.setFecha(request.getFecha());
        
        Reservacion reservacion = reservacionService.obtenerReservacionPorId(request.getIdReservacion());        
        fechaReservacion.setReservacion(reservacion);
        
        return fechaReservacion;        
    }

    // Obtener todas las fechas de reservación
    public List<FechaReservacion> obtenerTodasLasFechasReservacion() {
        return fechaReservacionRepository.findAll();
    }

    // Obtener una fecha de reservación por ID
    public FechaReservacion obtenerFechaReservacionPorId(Long id) {
        return fechaReservacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fecha de reservación no encontrada"));
    }

    // Eliminar una fecha de reservación
    public void eliminarFechaReservacion(Long id) {
        FechaReservacion fechaReservacion = obtenerFechaReservacionPorId(id);
        fechaReservacionRepository.delete(fechaReservacion);
    }

    // Convertir una fecha de reservación a DTO
    public FechaReservacionDto convertirFechaReservacionADto(FechaReservacion fechaReservacion) {
        return modelMapper.map(fechaReservacion, FechaReservacionDto.class);
    }

    // Convertir todas las fechas de reservación a DTOs
    public List<FechaReservacionDto> convertirTodasLasFechasADto(List<FechaReservacion> fechasReservacion) {
        return fechasReservacion.stream()
                .map(fecha -> modelMapper.map(fecha, FechaReservacionDto.class))
                .collect(Collectors.toList());
    }
}

