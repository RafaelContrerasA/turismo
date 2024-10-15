package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    // Puedes agregar métodos de consulta adicionales si es necesario
}

