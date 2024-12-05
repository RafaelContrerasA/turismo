package com.example.Integradoraturismo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoPatchRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
}

