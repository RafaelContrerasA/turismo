package com.example.Integradoraturismo.models;

import jakarta.persistence.Entity;

//import java.math.BigDecimal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarritoFechaReservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int cantidad;
    
    //private BigDecimal precio;
    
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;
    
    @ManyToOne
    @JoinColumn(name = "fecha_reservacion_id")
    private FechaReservacion fechaReservacion;
}
