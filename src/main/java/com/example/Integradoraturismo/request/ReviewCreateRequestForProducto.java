package com.example.Integradoraturismo.request;

import lombok.Data;

@Data
public class ReviewCreateRequestForProducto {
    private Long productoId;
    private float calificacion;
    private String comentarios;
    
}
