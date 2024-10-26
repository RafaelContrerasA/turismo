package com.example.Integradoraturismo.models;

import java.util.List;

public class ReporteCalificaciones {

    private String nombreProducto;
    private String nombreServicio;
    private double promedioCalificaciones;
    private List<String> comentarios;

    // Constructor
    public ReporteCalificaciones(String nombreProducto, String nombreServicio, double promedioCalificaciones, List<String> comentarios) {
        this.nombreProducto = nombreProducto;
        this.nombreServicio = nombreServicio;
        this.promedioCalificaciones = promedioCalificaciones;
        this.comentarios = comentarios;
    }

    // Getters y Setters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public double getPromedioCalificaciones() {
        return promedioCalificaciones;
    }

    public void setPromedioCalificaciones(double promedioCalificaciones) {
        this.promedioCalificaciones = promedioCalificaciones;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}