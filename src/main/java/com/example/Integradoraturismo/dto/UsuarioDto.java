package com.example.Integradoraturismo.dto;

import com.example.Integradoraturismo.models.Rol;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean esStaff;
    private boolean registrado;
    private Rol rol;

    // Getters y setters
}