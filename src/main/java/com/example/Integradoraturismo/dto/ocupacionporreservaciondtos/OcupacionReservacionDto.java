package com.example.Integradoraturismo.dto.ocupacionporreservaciondtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.Integradoraturismo.dto.ImagenDto;

import lombok.Data;

@Data
public class OcupacionReservacionDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String lugar;
    private BigDecimal precio;
    private List<ImagenDto> imagenes;
    //private EmpresaMiembroDto empresaMiembro;
    private List<OcupacionFechaReservacionDto> fechas;
}
