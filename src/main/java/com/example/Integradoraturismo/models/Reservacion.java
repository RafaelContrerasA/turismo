package com.example.Integradoraturismo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String descripcion;
    private String lugar;
    private BigDecimal precio;
    private String tipoReview;
    
    @Column(nullable = true)
    private int totalReviews=0;
    @Column(nullable = true)
    private float calificacion=0;
    
    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;
    
    @ManyToOne
    @JoinColumn(name = "empresa_miembro_id")
    private EmpresaMiembro empresaMiembro;
    
    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FechaReservacion> fechas;
    
    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}
