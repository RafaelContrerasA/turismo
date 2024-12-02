package com.example.Integradoraturismo.dto.ocupacionporreservaciondtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OcupacionPedidoFechaReservacionDto {
    private int cantidad;
    private BigDecimal precio;
    private OcupacionPedidoDto pedido;
}
