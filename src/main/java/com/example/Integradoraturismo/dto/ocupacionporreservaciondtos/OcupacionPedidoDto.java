package com.example.Integradoraturismo.dto.ocupacionporreservaciondtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.Integradoraturismo.dto.UsuarioDto;
import com.example.Integradoraturismo.enums.EstatusPedido;

import lombok.Data;

@Data
public class OcupacionPedidoDto {
    private Long id;
    private LocalDate fecha;
    private BigDecimal precioTotal;
    private EstatusPedido estatusPedido;
    private UsuarioDto usuario;
}
