package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.models.Review;
import com.example.Integradoraturismo.models.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductoId(Long idProducto);
    List<Review> findByReservacionId(Long idReservacion);
    List<Review> findByUsuario(Usuario usuario);
}