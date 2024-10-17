package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Sección para métodos adicionales personalizados.
}

