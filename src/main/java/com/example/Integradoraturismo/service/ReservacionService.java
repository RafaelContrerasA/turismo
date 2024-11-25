package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.ReservacionDto;
import com.example.Integradoraturismo.dto.catalogoreservacionesdtos.CatalogoReservacionDto;
import com.example.Integradoraturismo.dto.ocupacionporreservaciondtos.OcupacionReservacionDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.repository.ReservacionRepository;
import com.example.Integradoraturismo.request.ReservacionCreateRequest;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservacionService {

    private final ReservacionRepository reservacionRepository;
    private final ModelMapper modelMapper;

    // Crear o actualizar una reservación
    public Reservacion crearReservacion(ReservacionCreateRequest request) {
        Reservacion reservacion = transformarRequestAReservacion(request);
        return reservacionRepository.save(reservacion);
    }

    private Reservacion transformarRequestAReservacion(ReservacionCreateRequest request) {
        Reservacion reservacion = new Reservacion();
        reservacion.setDescripcion(request.getDescripcion());
        reservacion.setPrecio(request.getPrecio());
        return reservacion;
    }

    // Obtener una reservación por su ID
    public Reservacion obtenerReservacionPorId(Long id) {
        return reservacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservación no encontrada con el ID: " + id));
    }

    // Obtener todas las reservaciones
    public List<Reservacion> obtenerTodasLasReservaciones() {
        return reservacionRepository.findAll();
    }

    // Eliminar una reservación por su ID
    public void eliminarReservacion(Long id) {
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservación no encontrada con el ID: " + id));
        reservacionRepository.delete(reservacion);
    }

    // Convertir una Reservacion a ReservacionDto
    public ReservacionDto convertirReservacionADto(Reservacion reservacion) {
        return modelMapper.map(reservacion, ReservacionDto.class);
    }

    // Convertir todas las Reservaciones a ReservacionDtos
    public List<ReservacionDto> convertirTodasLasReservacionesADto(List<Reservacion> reservaciones) {
        return reservaciones.stream()
                .map(reservacion -> convertirReservacionADto(reservacion))
                .toList();
    }

    // La DTO para generar el catalogo para el cliente
    public CatalogoReservacionDto convertirReservacionACatalogoDto(Reservacion reservacion) {
        return modelMapper.map(reservacion, CatalogoReservacionDto.class);
    }

    // La DTO para generar el catalogo para el cliente
    public List<CatalogoReservacionDto> convertirTodasLasReservacionesACatalogoDto(List<Reservacion> reservaciones) {
        return reservaciones.stream()
                .map(reservacion -> convertirReservacionACatalogoDto(reservacion))
                .toList();
    }

    // La DTO que se usa si de la reservacion se desea conocer los cupos que se han apartado en cada una
    public OcupacionReservacionDto convertirReservacionAOcupacionDto(Reservacion reservacion) {
        return modelMapper.map(reservacion, OcupacionReservacionDto.class);
    }

    // La DTO que se usa si de la reservacion se desea conocer los cupos que se han apartado en cada una
    public List<OcupacionReservacionDto> convertirTodasLasReservacionesAOcupacionDto(List<Reservacion> reservaciones) {
        return reservaciones.stream()
                .map(reservacion -> convertirReservacionAOcupacionDto(reservacion))
                .toList();
    }
}
