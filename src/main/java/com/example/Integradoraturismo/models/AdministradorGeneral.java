package com.example.Integradoraturismo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores_generales")
public class AdministradorGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public void gestionarCuentas() {
    
    }

    public void visualizarReporteGeneral() {
        
    }
}
