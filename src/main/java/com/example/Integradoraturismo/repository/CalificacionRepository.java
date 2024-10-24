package com.example.Integradoraturismo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Integradoraturismo.models.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByEmpresaIdAndProductoIsNotNull(Long empresaId);
    List<Calificacion> findByEmpresaIdAndServicioIsNotNull(Long empresaId);
}
