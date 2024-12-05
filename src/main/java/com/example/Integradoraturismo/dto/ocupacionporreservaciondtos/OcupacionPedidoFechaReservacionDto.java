package com.example.Integradoraturismo.dto.ocupacionporreservaciondtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.Integradoraturismo.dto.TicketDto;
import lombok.Data;

@Data
public class OcupacionPedidoFechaReservacionDto {
    private int cantidad;
    private BigDecimal precio;
    private OcupacionPedidoDto pedido;
    private List<TicketDto> tickets;
}
