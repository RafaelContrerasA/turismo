package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.CarritoProducto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long> {
    void deleteAllByCarritoId(Long id);
}

