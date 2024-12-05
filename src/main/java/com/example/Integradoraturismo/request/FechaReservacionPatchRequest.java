
package com.example.Integradoraturismo.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FechaReservacionPatchRequest {
    private Integer cupoTotal;
    private Integer cupoDisponible;
    private LocalDateTime fecha;
    private Long reservacionId;
}

