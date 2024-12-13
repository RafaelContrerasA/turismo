package com.example.Integradoraturismo.controller;


import com.example.Integradoraturismo.dto.ReviewDto;
import com.example.Integradoraturismo.models.Review;
import com.example.Integradoraturismo.request.ReviewCreateRequestForProducto;
import com.example.Integradoraturismo.request.ReviewCreateRequestForReservacion;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.ReviewService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UsuarioService usuarioService;

    // Guardar una nueva Review para un Producto
    //@PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/producto")
    public ResponseEntity<ApiResponse> guardarReviewProducto(@RequestBody ReviewCreateRequestForProducto request) {
        try {
            Long usuarioId = usuarioService.obtenerIdUsuarioLogeado();
            Review review = reviewService.guardarReviewProducto(usuarioId, request);
            ReviewDto reviewDto = reviewService.convertirReviewADto(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Review creada con éxito.", reviewDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));

        }
        
    }

    // Guardar una nueva Review para una Reservación
    @PostMapping("/reservacion")
    //@PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ApiResponse> guardarReviewReservacion(@RequestBody ReviewCreateRequestForReservacion request) {
        try {
            Long usuarioId = usuarioService.obtenerIdUsuarioLogeado();
            Review review = reviewService.guardarReviewReservacion(usuarioId, request);
            ReviewDto reviewDto = reviewService.convertirReviewADto(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Review creada con éxito.", reviewDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));

        }
        
    }
    

    // Obtener todas las Reviews de un Producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<ApiResponse> obtenerReviewsPorProductoId(@PathVariable Long productoId) {
        
        try {
            List<Review> reviews = reviewService.obtenerReviewsPorProductoId(productoId);
            List<ReviewDto> reviewsDto = reviewService.convertirTodasLasReviewsADto(reviews);
            return ResponseEntity.ok(new ApiResponse("Reviews obtenidas con éxito.", reviewsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        
        
    }

    // Obtener todas las Reviews de una Reservación
    @GetMapping("/reservacion/{reservacionId}")
    public ResponseEntity<ApiResponse> obtenerReviewsPorReservacionId(@PathVariable Long reservacionId) {
        try {
            List<Review> reviews = reviewService.obtenerReviewsPorReservacionId(reservacionId);
            List<ReviewDto> reviewsDto = reviewService.convertirTodasLasReviewsADto(reviews);
            return ResponseEntity.ok(new ApiResponse("Reviews obtenidas con éxito.", reviewsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    //@PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/mis-reviews")
    public ResponseEntity<ApiResponse> obtenerReviewsPorUsuarioSesion() {
        try {
            Long usuarioId = usuarioService.obtenerIdUsuarioLogeado();
            List<Review> reviews = reviewService.obtenerReviewsPorUsuarioId(usuarioId);
            List<ReviewDto> reviewsDto = reviewService.convertirTodasLasReviewsADto(reviews);
            return ResponseEntity.ok(new ApiResponse("Reviews obtenidas con éxito.", reviewsDto));            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    // Obtener todas las Reviews hechas por un Usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse> obtenerReviewsPorUsuarioId(@PathVariable Long usuarioId) {
        try {
            List<Review> reviews = reviewService.obtenerReviewsPorUsuarioId(usuarioId);
            List<ReviewDto> reviewsDto = reviewService.convertirTodasLasReviewsADto(reviews);
            return ResponseEntity.ok(new ApiResponse("Reviews obtenidas con éxito.", reviewsDto));            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
    

}
