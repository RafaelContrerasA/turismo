package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Ticket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByFolio(String folio);
}
