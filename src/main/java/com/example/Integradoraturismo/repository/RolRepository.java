package com.example.Integradoraturismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Integradoraturismo.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
