package com.example.Integradoraturismo.repository;
import com.example.Integradoraturismo.models.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
}