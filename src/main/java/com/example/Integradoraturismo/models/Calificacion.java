package com.example.Integradoraturismo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = true)
    private Servicio servicio; // Agregar el campo Servicio

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "comentario", nullable = true)
    private String comentario;

    @Column(name = "valoracion", nullable = false)
    private int valoracion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // Constructor vacío
    public Calificacion() {}

    // Constructor con parámetros
    public Calificacion(Producto producto, Servicio servicio, Usuario usuario, String comentario, int valoracion, LocalDateTime fecha) {
        this.producto = producto;
        this.servicio = servicio;
        this.usuario = usuario;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Servicio getServicio() { return servicio; } // Agregar el getter
    public void setServicio(Servicio servicio) { this.servicio = servicio; } // Agregar el setter

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getValoracion() { return valoracion; }
    public void setValoracion(int valoracion) { this.valoracion = valoracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
