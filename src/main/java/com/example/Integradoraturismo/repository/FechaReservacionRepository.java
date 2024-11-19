package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.FechaReservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FechaReservacionRepository extends JpaRepository<FechaReservacion, Long> {
}