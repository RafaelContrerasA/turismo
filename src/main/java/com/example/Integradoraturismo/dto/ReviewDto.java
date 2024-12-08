package com.example.Integradoraturismo.dto;

import java.time.LocalDateTime;

import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.models.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReviewDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private LocalDateTime fecha;    
    private float calificacion;
    private String comentarios;
    private UsuarioDto usuario;
}
