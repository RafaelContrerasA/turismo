package com.example.Integradoraturismo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Integradoraturismo.models.Ocupacion;
import com.example.Integradoraturismo.services.OcupacionService;

@RestController
@RequestMapping("/empresas/{id}/reportes")
public class OcupacionController {

    @Autowired
    private OcupacionService ocupacionService;

    @GetMapping("/ocupacion")
    public ResponseEntity<List<Ocupacion>> obtenerReporteOcupacion(
            @PathVariable("id") Long empresaId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        List<Ocupacion> reporte = ocupacionService.obtenerReporteOcupacion(empresaId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
