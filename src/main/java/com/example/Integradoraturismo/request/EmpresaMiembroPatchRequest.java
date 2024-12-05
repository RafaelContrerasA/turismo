package com.example.Integradoraturismo.request;

import lombok.Data;

@Data
public class EmpresaMiembroPatchRequest {
    private String nombre;
    private String correo;
    private String telefono;
}
