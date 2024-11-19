package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.CarritoFechaReservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoFechaReservacionRepository extends JpaRepository<CarritoFechaReservacion, Long> {
    void deleteAllByCarritoId(Long id);
}
