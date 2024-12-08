package com.example.Integradoraturismo.dto.catalogoreservacionesdtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.Integradoraturismo.dto.EmpresaMiembroDto;
import com.example.Integradoraturismo.dto.ImagenDto;
import com.example.Integradoraturismo.dto.ReviewDto;

import lombok.Data;
@Data
public class CatalogoReservacionDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String lugar;
    private int totalReviews;
    private float calificacion;
    private BigDecimal precio;
    private List<ImagenDto> imagenes;
    private EmpresaMiembroDto empresaMiembro;
    private List<CatalogoFechaReservacionDto> fechas;
    private List<ReviewDto> reviews;
}
