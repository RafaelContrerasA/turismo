package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    // Método para encontrar calificaciones por producto
    List<Calificacion> findByProductoId(Long productoId);
}

