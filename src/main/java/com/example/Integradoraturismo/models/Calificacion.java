package com.example.Integradoraturismo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Producto, una calificación puede pertenecer a un producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Relación con Servicio, una calificación puede pertenecer a un servicio
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;  // Calificación de 1 a 5, por ejemplo.

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    // Constructor vacío
    public Calificacion() {}

    // Constructor con parámetros
    public Calificacion(Producto producto, Servicio servicio, String comentario, int calificacion, Date fecha) {
        this.producto = producto;
        this.servicio = servicio;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
