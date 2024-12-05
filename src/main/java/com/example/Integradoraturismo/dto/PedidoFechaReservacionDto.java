package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;
import java.util.List;


import lombok.Data;

@Data
public class PedidoFechaReservacionDto {
    private int cantidad;
    private BigDecimal precio;
    private FechaReservacionDto fechaReservacion;
    private List<TicketDto> tickets;
}