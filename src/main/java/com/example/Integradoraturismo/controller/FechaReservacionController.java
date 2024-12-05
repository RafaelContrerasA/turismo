package com.example.Integradoraturismo.controller;


import com.example.Integradoraturismo.dto.FechaReservacionDto;
import com.example.Integradoraturismo.models.FechaReservacion;
import com.example.Integradoraturismo.request.FechaReservacionCreateRequest;
import com.example.Integradoraturismo.request.FechaReservacionPatchRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.FechaReservacionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fechas-reservacion")
public class FechaReservacionController {

    private final FechaReservacionService fechaReservacionService;

    // Crear una nueva fecha de reservación
    @PostMapping
    public ResponseEntity<ApiResponse> crearFechaReservacion(@RequestBody FechaReservacionCreateRequest request) {
        FechaReservacion nuevaFechaReservacion = fechaReservacionService.crearFechaReservacion(request);
        FechaReservacionDto fechaReservacionDto = fechaReservacionService.convertirFechaReservacionADto(nuevaFechaReservacion);
        return new ResponseEntity<>(new ApiResponse("Fecha de reservación creada con éxito", fechaReservacionDto), HttpStatus.CREATED);
    }

    // Obtener todas las fechas de reservación
    @GetMapping
    public ResponseEntity<ApiResponse> obtenerTodasLasFechasReservacion() {
        List<FechaReservacion> fechasReservacion = fechaReservacionService.obtenerTodasLasFechasReservacion();
        List<FechaReservacionDto> fechaReservacionDtos = fechaReservacionService.convertirTodasLasFechasADto(fechasReservacion);
        return ResponseEntity.ok(new ApiResponse("success", fechaReservacionDtos));
    }

    // Obtener una fecha de reservación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerFechaReservacionPorId(@PathVariable Long id) {
        FechaReservacion fechaReservacion = fechaReservacionService.obtenerFechaReservacionPorId(id);
        FechaReservacionDto fechaReservacionDto = fechaReservacionService.convertirFechaReservacionADto(fechaReservacion);
        return ResponseEntity.ok(new ApiResponse("success", fechaReservacionDto));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> actualizarFechaReservacionParcialmente(@PathVariable Long id, @RequestBody FechaReservacionPatchRequest request) {
        try {
            FechaReservacion fechaReservacionActualizada = fechaReservacionService.actualizarFechaReservacionParcialmente(id, request);
            FechaReservacionDto fechaReservacionDto = fechaReservacionService.convertirFechaReservacionADto(fechaReservacionActualizada);
            return ResponseEntity.ok(new ApiResponse("Fecha de reservación actualizada con éxito", fechaReservacionDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    // Eliminar una fecha de reservación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarFechaReservacion(@PathVariable Long id) {
        fechaReservacionService.eliminarFechaReservacion(id);
        return ResponseEntity.ok(new ApiResponse("Fecha de reservación eliminada con éxito", null));
    }
}

