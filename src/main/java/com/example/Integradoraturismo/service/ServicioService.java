package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.models.Servicio;
import com.example.Integradoraturismo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Crear un nuevo servicio
    public Servicio crearServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Obtener un servicio por ID
    public Servicio obtenerServicio(Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.orElse(null); // Devuelve null si no se encuentra
    }

    // Actualizar un servicio existente
    public Servicio actualizarServicio(Long id, Servicio servicioActualizado) {
        Servicio servicio = obtenerServicio(id);
        if (servicio != null) {
            servicio.setDuracion(servicioActualizado.getDuracion());
            servicio.setTipo(servicioActualizado.getTipo());
            return servicioRepository.save(servicio);
        }
        return null; // Devuelve null si no se encuentra
    }

    // Eliminar un servicio por ID
    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }

    // Listar todos los servicios
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }
}
