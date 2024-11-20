package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoFechaReservacionDto {
    private int cantidad;
    private BigDecimal precio;
    private FechaReservacionDto fechaReservacion;
}