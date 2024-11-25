package com.example.Integradoraturismo.dto.catalogoreservacionesdtos;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class CatalogoFechaReservacionDto {
    private Long id;
    private int cupoDisponible;
    private LocalDateTime fecha;
}
