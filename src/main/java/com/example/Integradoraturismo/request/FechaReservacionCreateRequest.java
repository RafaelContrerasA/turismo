package com.example.Integradoraturismo.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FechaReservacionCreateRequest {
    private int cupoDisponible;
    private LocalDateTime fecha;
    private Long idReservacion;
    
}
