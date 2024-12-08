package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.ReviewDto;
import com.example.Integradoraturismo.enums.TipoReview;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.models.Review;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.ProductoRepository;
import com.example.Integradoraturismo.repository.ReservacionRepository;
import com.example.Integradoraturismo.repository.ReviewRepository;
import com.example.Integradoraturismo.request.ReviewCreateRequestForProducto;
import com.example.Integradoraturismo.request.ReviewCreateRequestForReservacion;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;
    private final ReservacionRepository reservacionRepository;
    private final ReservacionService reservacionService;
    private final ModelMapper modelMapper;

    // Guardar una nueva Review
    @Transactional
    public Review guardarReviewProducto(Long usuarioId, ReviewCreateRequestForProducto request) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        Producto producto = productoService.obtenerProductoPorId(request.getProductoId());

        Review review = new Review();

        review.setProducto(producto);
        review.setUsuario(usuario);
        review.setTipoReview(TipoReview.PRODUCTO);
        review.setCalificacion(request.getCalificacion());
        review.setComentarios(request.getComentarios());
        review.setFecha(LocalDateTime.now());

        // Cambiar tipo de calificacion y totalReviews a Float y Integer respectivamente
        Float calificacion = producto.getCalificacion(); // Ahora es de tipo Float
        Integer totalReviews = producto.getTotalReviews(); // Ahora es de tipo Integer

        // Verificar si son null y asignar valores por defecto
        calificacion = (calificacion != null) ? calificacion : 0.0f;
        totalReviews = (totalReviews != null) ? totalReviews : 0;

        float nuevoPromedio = ((calificacion * totalReviews) + request.getCalificacion()) / (totalReviews + 1);

        producto.setCalificacion(nuevoPromedio);
        producto.setTotalReviews(totalReviews + 1);
        productoRepository.save(producto);

        return reviewRepository.save(review);

    }

    @Transactional
    public Review guardarReviewReservacion(Long usuarioId, ReviewCreateRequestForReservacion request) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        Reservacion reservacion = reservacionService.obtenerReservacionPorId(request.getReservacionId());

        Review review = new Review();

        review.setReservacion(reservacion);
        review.setUsuario(usuario);
        review.setTipoReview(TipoReview.RERSERVACION);
        review.setCalificacion(request.getCalificacion());
        review.setComentarios(request.getComentarios());
        review.setFecha(LocalDateTime.now());

        // Cambiar tipo de calificacion y totalReviews a Float y Integer respectivamente
        Float calificacion = reservacion.getCalificacion(); // Ahora es de tipo Float
        Integer totalReviews = reservacion.getTotalReviews(); // Ahora es de tipo Integer

        // Verificar si son null y asignar valores por defecto
        calificacion = (calificacion != null) ? calificacion : 0.0f;
        totalReviews = (totalReviews != null) ? totalReviews : 0;

        float nuevoPromedio = ((calificacion * totalReviews) + request.getCalificacion()) / (totalReviews + 1);

        reservacion.setCalificacion(nuevoPromedio);
        reservacion.setTotalReviews(totalReviews + 1);
        reservacionRepository.save(reservacion);

        return reviewRepository.save(review);

    }

    // Obtener una Review por ID
    public Review obtenerReviewPorId(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review con ID " + id + " no encontrada."));
    }

    // Obtener todas las Reviews de un Producto por su ID
    public List<Review> obtenerReviewsPorProductoId(Long productoId) {
        List<Review> reviews = reviewRepository.findByProductoId(productoId);
        return reviews;
    }

    // Obtener todas las Reviews de una Reservación por su ID
    public List<Review> obtenerReviewsPorReservacionId(Long reservacionId) {
        List<Review> reviews = reviewRepository.findByReservacionId(reservacionId);
        return reviews;
    }

    // **Nuevo Método: Obtener todas las Reviews hechas por un Usuario**
    public List<Review> obtenerReviewsPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        List<Review> reviews = reviewRepository.findByUsuario(usuario);
        return reviews;
    }

    // Convertir Review a DTO
    public ReviewDto convertirReviewADto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    // Convertir una lista de Reviews a DTO
    public List<ReviewDto> convertirTodasLasReviewsADto(List<Review> reviews) {
        return reviews.stream()
                .map(this::convertirReviewADto)
                .collect(Collectors.toList());
    }

    // Obtener todas las Reviews en formato DTO
    public List<ReviewDto> obtenerTodasLasReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return convertirTodasLasReviewsADto(reviews);
    }

    // Actualizar una Review
    public Review actualizarReview(Long id, Review reviewDetalles) {
        Review review = obtenerReviewPorId(id);
        review.setFecha(reviewDetalles.getFecha());
        review.setCalificacion(reviewDetalles.getCalificacion());
        review.setComentarios(reviewDetalles.getComentarios());
        review.setReservacion(reviewDetalles.getReservacion());
        review.setProducto(reviewDetalles.getProducto());
        review.setUsuario(reviewDetalles.getUsuario());
        return reviewRepository.save(review);
    }

    // Eliminar una Review
    public void eliminarReview(Long id) {
        Review review = obtenerReviewPorId(id);
        reviewRepository.delete(review);
    }
}
