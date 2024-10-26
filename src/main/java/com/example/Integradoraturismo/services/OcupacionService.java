package com.example.Integradoraturismo.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Integradoraturismo.models.Ocupacion;
import com.example.Integradoraturismo.repository.OcupacionRepository;

@Service
public class OcupacionService {

    @Autowired
    private OcupacionRepository ocupacionRepository;

    public List<Ocupacion> obtenerReporteOcupacion(Long empresaId, LocalDate fechaInicio, LocalDate fechaFin) {
        return ocupacionRepository.findByEmpresaIdAndFechaInicioBetween(empresaId, fechaInicio, fechaFin);
    }
}
