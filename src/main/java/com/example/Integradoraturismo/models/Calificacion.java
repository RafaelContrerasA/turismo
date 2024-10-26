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
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Opcionalmente puedes añadir Servicio o Usuario según se requiera
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "comentario", nullable = true)
    private String comentario;

    @Column(name = "valoracion", nullable = false)
    private int valoracion; // Por ejemplo, de 1 a 5 estrellas

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // Constructor vacío
    public Calificacion() {}

    // Constructor con parámetros
    public Calificacion(Producto producto, Usuario usuario, String comentario, int valoracion, LocalDateTime fecha) {
        this.producto = producto;
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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getValoracion() { return valoracion; }
    public void setValoracion(int valoracion) { this.valoracion = valoracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
