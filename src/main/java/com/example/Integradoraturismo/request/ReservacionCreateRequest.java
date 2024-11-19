package com.example.Integradoraturismo.request;

import java.math.BigDecimal;
import java.util.List;

import com.example.Integradoraturismo.dto.ImagenDto;

import lombok.Data;
 
@Data
public class ReservacionCreateRequest {
    private String descripcion;
    private BigDecimal precio;

    // Getters y setters
}
