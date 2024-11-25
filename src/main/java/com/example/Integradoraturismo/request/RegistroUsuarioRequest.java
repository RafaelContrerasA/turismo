package com.example.Integradoraturismo.request;

import lombok.Data;

@Data
public class RegistroUsuarioRequest {
    private String email;
    private String nombre;
    private String password;
    private String telefono;
    
}
