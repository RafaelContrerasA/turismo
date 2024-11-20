package com.example.Integradoraturismo.dto;

import lombok.Data;

@Data
public class CarritoFechaReservacionDto {
    private int cantidad;
    private FechaReservacionDto fechaReservacion;

}