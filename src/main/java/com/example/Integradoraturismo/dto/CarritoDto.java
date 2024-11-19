package com.example.Integradoraturismo.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CarritoDto {
    private Long id;
    private UsuarioDto usuario;
    //private Long usuarioId;    
    private Set<CarritoFechaReservacionDto> reservaciones;
    private BigDecimal total;
}