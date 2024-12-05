package com.example.Integradoraturismo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FechaReservacionDto {
    private Long id;
    private int cupoTotal;
    private int cupoDisponible;
    private LocalDateTime fecha;
    private ReservacionDto reservacion;
}