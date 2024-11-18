package com.example.Integradoraturismo.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.example.Integradoraturismo.enums.EstatusPedido;

import lombok.Data;

@Data
public class PedidoDto {
    private Long id;
    private LocalDate fecha;
    private BigDecimal precioTotal;
    private EstatusPedido estatusPedido;
    private UsuarioDto usuario;
    private Set<PedidoFechaReservacionDto> reservaciones;
}