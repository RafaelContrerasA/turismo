package com.example.Integradoraturismo.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CarritoDto {
    private Long id;
    private UsuarioDto usuario;
    private Set<CarritoFechaReservacionDto> reservaciones;
}