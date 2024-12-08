package com.example.Integradoraturismo.dto;

import java.util.List;
import lombok.Data;


@Data
public class ProductoDto {
    private Long id; 
    private String nombre;
    private String descripcion;
    private float precio;
    private int stock;
    private int totalReviews;
    private float calificacion;    
    private EmpresaMiembroDto empresaMiembro;
    private List<ImagenProductoDto> imagenes;
    private List<ReviewDto> reviews;
    

}
