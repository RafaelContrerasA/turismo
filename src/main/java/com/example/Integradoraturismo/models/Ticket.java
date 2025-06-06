package com.example.Integradoraturismo.models;

import com.example.Integradoraturismo.enums.EstatusPedido;
import com.example.Integradoraturismo.enums.EstatusTicket;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String folio;
    
    @Enumerated(EnumType.STRING)
    private EstatusTicket estatus;
    
    @ManyToOne 
    @JoinColumn(name = "pedido_fecha_reservacion_id")
    private PedidoFechaReservacion pedidoFechaReservacion;
}
