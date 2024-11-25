package com.example.Integradoraturismo.models;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FechaReservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cupoTotal;
    private int cupoDisponible;
    private LocalDateTime fecha;
    
    @ManyToOne
    @JoinColumn(name = "reservacion_id")
    private Reservacion reservacion;
    
    @OneToMany(mappedBy = "fechaReservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoFechaReservacion> pedidosHechos;
    
}
