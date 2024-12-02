package com.example.Integradoraturismo.service;


import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.repository.EmpresaMiembroRepository;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmpresaMiembroService {

    private final EmpresaMiembroRepository empresaMiembroRepository;

    // Método para obtener todos los registros de empresas miembros
    public List<EmpresaMiembro> obtenerTodasLasEmpresas() {
        return empresaMiembroRepository.findAll();
    }

    // Método para obtener una empresa miembro por su id
    public EmpresaMiembro obtenerEmpresaPorId(Long id) {
        return empresaMiembroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con id: " + id));
    }

    // Método para crear una nueva empresa miembro
    public EmpresaMiembro crearEmpresa(EmpresaMiembro empresaMiembro) {
        return empresaMiembroRepository.save(empresaMiembro);
    }

    // Método para actualizar una empresa miembro
    public EmpresaMiembro actualizarEmpresa(Long id, EmpresaMiembro detallesEmpresa) {
        EmpresaMiembro empresaExistente = obtenerEmpresaPorId(id);
        empresaExistente.setNombre(detallesEmpresa.getNombre());
        empresaExistente.setCorreo(detallesEmpresa.getCorreo());
        empresaExistente.setTelefono(detallesEmpresa.getTelefono());
        return empresaMiembroRepository.save(empresaExistente);
    }

    // Método para eliminar una empresa miembro
    public void eliminarEmpresa(Long id) {
        EmpresaMiembro empresa = obtenerEmpresaPorId(id);
        empresaMiembroRepository.delete(empresa);
    }
}
