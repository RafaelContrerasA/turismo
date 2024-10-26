package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.models.Calificacion;
import com.example.Integradoraturismo.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    // Crear una calificación
    public Calificacion crearCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    // Obtener calificaciones de un producto específico
    public List<Calificacion> obtenerCalificacionesPorProducto(Long productoId) {
        return calificacionRepository.findByProductoId(productoId);
    }
}
