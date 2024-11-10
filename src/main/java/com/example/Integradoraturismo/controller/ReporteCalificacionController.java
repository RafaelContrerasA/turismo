// package com.example.Integradoraturismo.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.Integradoraturismo.models.ReporteCalificaciones;
// import com.example.Integradoraturismo.service.ReporteCalificacionesService;

// @RestController
// @RequestMapping("/api/empresas")
// public class ReporteCalificacionController {

//     @Autowired
//     private ReporteCalificacionesService reporteCalificacionesService;

//     @GetMapping("/{id}/reportes/calificaciones")
//     public ResponseEntity<List<ReporteCalificaciones>> obtenerReporteCalificaciones(@PathVariable Long id) {
//         List<ReporteCalificaciones> reporte = reporteCalificacionesService.generarReporte(id);
//         return ResponseEntity.ok(reporte);
//     }
// }
