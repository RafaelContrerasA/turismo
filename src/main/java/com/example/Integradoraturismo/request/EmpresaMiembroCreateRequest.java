package com.example.Integradoraturismo.request;

import lombok.Data;

@Data
public class EmpresaMiembroCreateRequest {
    private String nombre;
    private String correo;
    private String telefono;
}
