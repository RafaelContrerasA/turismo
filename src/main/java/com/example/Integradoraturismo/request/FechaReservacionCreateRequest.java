package com.example.Integradoraturismo.request;

import java.time.LocalDateTime;

import com.example.Integradoraturismo.models.Reservacion;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class FechaReservacionCreateRequest {
    private int cupoDisponible;
    private LocalDateTime fecha;
    private Long idReservacion;
    
}
