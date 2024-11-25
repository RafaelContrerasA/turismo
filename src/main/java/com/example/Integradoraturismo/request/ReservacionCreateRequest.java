package com.example.Integradoraturismo.request;

import java.math.BigDecimal;


import lombok.Data;
 
@Data
public class ReservacionCreateRequest {
    private String nombre;
    private String descripcion;
    private String lugar;
    private BigDecimal precio;

}
