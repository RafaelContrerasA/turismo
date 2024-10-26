package com.example.Integradoraturismo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Cambiado a Long para escalabilidad

    @Column(name = "duracion")
    private int duracion;

    @Column(name = "tipo")
    private String tipo;

    // Constructor vacío
    public Servicio() {}

    // Constructor con parámetros
    public Servicio(int duracion, String tipo) {
        this.duracion = duracion;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() {  // Cambiado a Long
        return id;
    }

    public void setId(Long id) {  // Cambiado a Long
        this.id = id;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

