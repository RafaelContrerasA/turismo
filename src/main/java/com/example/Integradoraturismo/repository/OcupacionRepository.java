package com.example.Integradoraturismo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Integradoraturismo.models.Ocupacion;

@Repository
public interface OcupacionRepository extends JpaRepository<Ocupacion, Long> {
    List<Ocupacion> findByEmpresaIdAndFechaInicioBetween(Long empresaId, LocalDate fechaInicio, LocalDate fechaFin);
}
