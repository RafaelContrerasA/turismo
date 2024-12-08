package com.example.Integradoraturismo.models;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Integradoraturismo.enums.EstatusTicket;
import com.example.Integradoraturismo.enums.TipoReview;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fecha;
    
    @Column(nullable = true)
    private float calificacion;
    
    @Column(nullable = true)
    private String comentarios;
    
    @Enumerated(EnumType.STRING)
    private TipoReview tipoReview;
    
    @ManyToOne
    @JoinColumn(name = "reservacion_id", nullable = true)
    private Reservacion reservacion;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
}
