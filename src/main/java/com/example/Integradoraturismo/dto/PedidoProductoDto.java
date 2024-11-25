package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoProductoDto {
    private int cantidad;
    private BigDecimal precio;
    private ProductoDto producto;
}
