package com.example.Integradoraturismo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReservacionPatchRequest {
    private String nombre;
    private String descripcion;
    private String lugar;
    private BigDecimal precio;
    private Long empresaMiembroId;
}
