// package com.example.Integradoraturismo.controller;

// import com.example.Integradoraturismo.models.Calificacion;
// import com.example.Integradoraturismo.models.Producto;
// import com.example.Integradoraturismo.service.CalificacionService;
// import com.example.Integradoraturismo.service.ProductoService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/productos/{productoId}/calificaciones")
// public class CalificacionController {

//     @Autowired
//     private CalificacionService calificacionService;

//     @Autowired
//     private ProductoService productoService;

//     // Enviar una calificación
//     @PostMapping("/calificar")
//     public ResponseEntity<Calificacion> calificarProducto(
//             @PathVariable Long productoId, @RequestBody Calificacion calificacion) {

//         Optional<Producto> productoOpt = productoService.obtenerProducto(productoId);
//         if (!productoOpt.isPresent()) {
//             return ResponseEntity.notFound().build(); // Producto no encontrado, devuelve 404
//         }

//         Producto producto = productoOpt.get();
//         calificacion.setProducto(producto);
//         calificacion.setFecha(LocalDateTime.now()); // Establecer la fecha actual
//         Calificacion nuevaCalificacion = calificacionService.crearCalificacion(calificacion);

//         return ResponseEntity.ok(nuevaCalificacion);
//     }

//     // Ver las calificaciones de un producto
//     @GetMapping
//     public ResponseEntity<List<Calificacion>> obtenerCalificaciones(@PathVariable Long productoId) {
//         List<Calificacion> calificaciones = calificacionService.obtenerCalificacionesPorProducto(productoId);
//         return ResponseEntity.ok(calificaciones);
//     }
// }
