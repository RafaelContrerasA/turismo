package com.example.Integradoraturismo.dto;

import lombok.Data;

@Data
public class CarritoFechaReservacionDto {
    private Long id;
    private int cantidad;
    private FechaReservacionDto fechaReservacion;

}