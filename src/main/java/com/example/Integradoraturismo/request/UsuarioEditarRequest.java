package com.example.Integradoraturismo.request;

import lombok.Data;

@Data
public class UsuarioEditarRequest {
    private String email;
    private String nombre;
    private String password;
    private String telefono;
    private Long rolId;
    private boolean esStaff;
    private boolean registrado;
    private Long empresaId;
}

