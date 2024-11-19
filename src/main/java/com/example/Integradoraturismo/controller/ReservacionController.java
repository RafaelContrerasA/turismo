package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.dto.ReservacionDto;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.request.ReservacionCreateRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.ReservacionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservaciones")
public class ReservacionController {

    private final ReservacionService reservacionService;

    // Crear una nueva reservación
    @PostMapping
    public ResponseEntity<ApiResponse> crearReservacion(@RequestBody ReservacionCreateRequest request) {
        Reservacion nuevaReservacion = reservacionService.crearReservacion(request);
        ReservacionDto reservacionDto = reservacionService.convertirReservacionADto(nuevaReservacion);
        return new ResponseEntity<>(new ApiResponse("Reservación creada con éxito", reservacionDto), HttpStatus.CREATED);
    }

    // Obtener todas las reservaciones
    @GetMapping
    public ResponseEntity<ApiResponse> obtenerTodasLasReservaciones() {
        List<Reservacion> reservaciones = reservacionService.obtenerTodasLasReservaciones();
        List<ReservacionDto> reservacionDtos = reservacionService.convertirTodasLasReservacionesADto(reservaciones);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDtos));
    }

    // Obtener una reservación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerReservacionPorId(@PathVariable Long id) {
        Reservacion reservacion = reservacionService.obtenerReservacionPorId(id);
        ReservacionDto reservacionDto = reservacionService.convertirReservacionADto(reservacion);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDto));
    }

    // Eliminar una reservación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarReservacion(@PathVariable Long id) {
        reservacionService.eliminarReservacion(id);
        return ResponseEntity.ok(new ApiResponse("Reservación eliminada con éxito", null));
    }
}

