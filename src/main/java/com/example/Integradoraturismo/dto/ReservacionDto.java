package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ReservacionDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String lugar;
    private BigDecimal precio;
    private int totalReviews;
    private float calificacion;
    private List<ImagenDto> imagenes;
    private EmpresaMiembroDto empresaMiembro;
    private List<ReviewDto> reviews;
}