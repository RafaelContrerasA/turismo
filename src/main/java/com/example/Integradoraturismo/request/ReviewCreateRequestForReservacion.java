package com.example.Integradoraturismo.request;

import lombok.Data;

@Data	
public class ReviewCreateRequestForReservacion {
    private Long reservacionId;
    private float calificacion;
    private String comentarios;
}
