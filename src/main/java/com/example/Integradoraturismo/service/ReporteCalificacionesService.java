// package com.example.Integradoraturismo.service;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.Integradoraturismo.models.Calificacion;
// import com.example.Integradoraturismo.models.Producto;
// import com.example.Integradoraturismo.models.ReporteCalificaciones;
// import com.example.Integradoraturismo.models.Servicio;
// import com.example.Integradoraturismo.repository.CalificacionRepository;

// @Service
// public class ReporteCalificacionesService {

//     @Autowired
//     private CalificacionRepository calificacionRepository;

//     public List<ReporteCalificaciones> generarReporte(Long empresaId) {
//         List<ReporteCalificaciones> reportes = new ArrayList<>();

//         // Obtener calificaciones de productos por empresa
//         List<Calificacion> calificacionesProductos = calificacionRepository.findByEmpresaIdAndProductoIsNotNull(empresaId);

//         // Agrupar por producto y calcular promedio
//         Map<Producto, List<Calificacion>> agrupadoPorProducto = calificacionesProductos.stream()
//                 .collect(Collectors.groupingBy(Calificacion::getProducto));

//         // Procesar datos para productos
//         for (Map.Entry<Producto, List<Calificacion>> entry : agrupadoPorProducto.entrySet()) {
//             Producto producto = entry.getKey();
//             List<Calificacion> calificaciones = entry.getValue();

//             double promedio = calificaciones.stream()
//                     .mapToInt(Calificacion::getCalificacion)
//                     .average()
//                     .orElse(0.0);

//             List<String> comentarios = calificaciones.stream()
//                     .map(Calificacion::getComentario)
//                     .collect(Collectors.toList());

//             reportes.add(new ReporteCalificaciones(producto.getNombre(), null, promedio, comentarios));
//         }

//         // Obtener calificaciones de servicios
//         List<Calificacion> calificacionesServicios = calificacionRepository.findByEmpresaIdAndServicioIsNotNull(empresaId);

//         // Agrupar por servicio y calcular promedio
//         Map<Servicio, List<Calificacion>> agrupadoPorServicio = calificacionesServicios.stream()
//                 .collect(Collectors.groupingBy(Calificacion::getServicio));

//         // Procesar datos para servicios
//         for (Map.Entry<Servicio, List<Calificacion>> entry : agrupadoPorServicio.entrySet()) {
//             Servicio servicio = entry.getKey();
//             List<Calificacion> calificaciones = entry.getValue();

//             double promedio = calificaciones.stream()
//                     .mapToInt(Calificacion::getCalificacion)
//                     .average()
//                     .orElse(0.0);

//             List<String> comentarios = calificaciones.stream()
//                     .map(Calificacion::getComentario)
//                     .collect(Collectors.toList());

//             reportes.add(new ReporteCalificaciones(null, servicio.getTipo(), promedio, comentarios));
//         }

//         return reportes;
//     }
// }

