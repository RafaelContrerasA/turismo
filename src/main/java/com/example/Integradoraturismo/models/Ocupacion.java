package com.example.Integradoraturismo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ocupacion")
public class Ocupacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaMiembro empresa;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "tipo_servicio", nullable = false)
    private String tipoServicio;

    @Column(name = "porcentaje_ocupacion", nullable = false)
    private int porcentajeOcupacion;

    
    public Ocupacion() {}

    // Parameterized constructor
    public Ocupacion(EmpresaMiembro empresa, LocalDate fechaInicio, LocalDate fechaFin, String tipoServicio, int porcentajeOcupacion) {
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoServicio = tipoServicio;
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    // Getters and Setters nota: quiero morir
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpresaMiembro getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaMiembro empresa) {
        this.empresa = empresa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void setPorcentajeOcupacion(int porcentajeOcupacion) {
        this.porcentajeOcupacion = porcentajeOcupacion;
    }
}
