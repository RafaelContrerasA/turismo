package com.example.Integradoraturismo.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor vacío
@AllArgsConstructor // Genera un constructor con todos los campos
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cambiado a Long para consistencia.

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private int stock;
    
    @Column(nullable = true)
    private int totalReviews=0;
    
    @Column(nullable = true)
    private float calificacion=0;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenProducto> imagenes;
    
    
    @ManyToOne
    @JoinColumn(name = "empresa_miembro_id", nullable = true)
    private EmpresaMiembro empresaMiembro;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidosHechos;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
