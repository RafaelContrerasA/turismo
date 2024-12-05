package com.example.Integradoraturismo.dto.ocupacionporreservaciondtos;

import java.time.LocalDateTime;
import java.util.List;



import lombok.Data;
@Data	
public class OcupacionFechaReservacionDto {
    private Long id;
    private int cupoTotal;
    private int cupoDisponible;
    private LocalDateTime fecha;
    private List<OcupacionPedidoFechaReservacionDto> pedidosHechos;
}
