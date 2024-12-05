package com.example.Integradoraturismo.dto;

import com.example.Integradoraturismo.enums.EstatusTicket;

import lombok.Data;

@Data
public class TicketDto {
    private long id;
    private String folio;    
    private EstatusTicket estatus;
    
}
