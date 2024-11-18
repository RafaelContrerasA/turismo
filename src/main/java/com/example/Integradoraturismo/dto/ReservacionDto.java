package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ReservacionDto {
    private Long id;
    private String descripcion;
    private BigDecimal precio;
    private List<ImagenDto> imagenes;

    // Getters y setters
}