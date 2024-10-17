package com.example.Integradoraturismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Integradoraturismo.models.EmpresaMiembro;

@Repository
public interface EmpresaMiembroRepository extends JpaRepository<EmpresaMiembro, Long> {
}
