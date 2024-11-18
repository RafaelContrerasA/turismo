package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoFechaReservacionDto {
    private Long id;
    private int cantidad;
    private BigDecimal precio;
    private PedidoDto pedido;
    private FechaReservacionDto fechaReservacion;
}