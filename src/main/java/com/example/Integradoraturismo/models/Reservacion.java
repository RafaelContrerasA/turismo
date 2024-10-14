package com.example.Integradoraturismo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "reservaciones")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "estado")
    private String estado;

    // Constructor por defecto
    public Reservacion() {}

    // Constructor con parámetros
    public Reservacion(Date fecha, int cantidad, String estado) {
        super();
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Métodos específicos de la reservación
    public void confirmarReservacion() {
        // Lógica para confirmar la reservación
    }

    public void cancelarReservacion() {
        // Lógica para cancelar la reservación
    }
}
