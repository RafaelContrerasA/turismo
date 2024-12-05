package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.repository.EmpresaMiembroRepository;
import com.example.Integradoraturismo.request.EmpresaMiembroCreateRequest;
import com.example.Integradoraturismo.request.EmpresaMiembroPatchRequest;
import com.example.Integradoraturismo.dto.EmpresaMiembroDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmpresaMiembroService {

    private final EmpresaMiembroRepository empresaMiembroRepository;
    private final ModelMapper modelMapper;

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
    public EmpresaMiembro crearEmpresa(EmpresaMiembroCreateRequest request) {
        EmpresaMiembro empresa = new EmpresaMiembro();
        empresa.setNombre(request.getNombre());
        empresa.setCorreo(request.getCorreo());
        empresa.setTelefono(request.getTelefono());

        return empresaMiembroRepository.save(empresa);
    }

    // Método para actualizar una empresa miembro
    public EmpresaMiembro actualizarEmpresa(Long id, EmpresaMiembro detallesEmpresa) {
        EmpresaMiembro empresaExistente = obtenerEmpresaPorId(id);
        empresaExistente.setNombre(detallesEmpresa.getNombre());
        empresaExistente.setCorreo(detallesEmpresa.getCorreo());
        empresaExistente.setTelefono(detallesEmpresa.getTelefono());
        return empresaMiembroRepository.save(empresaExistente);
    }

    public EmpresaMiembro actualizarEmpresaParcialmente(Long id, EmpresaMiembroPatchRequest request) {
        EmpresaMiembro empresa = obtenerEmpresaPorId(id);

        if (request.getNombre() != null) {
            empresa.setNombre(request.getNombre());
        }
        if (request.getCorreo() != null) {
            empresa.setCorreo(request.getCorreo());
        }
        if (request.getTelefono() != null) {
            empresa.setTelefono(request.getTelefono());
        }

        return empresaMiembroRepository.save(empresa);
    }

    // Método para eliminar una empresa miembro
    public void eliminarEmpresa(Long id) {
        EmpresaMiembro empresa = obtenerEmpresaPorId(id);
        empresaMiembroRepository.delete(empresa);
    }

    // Método para convertir una entidad EmpresaMiembro a EmpresaMiembroDto
    public EmpresaMiembroDto convertirEmpresaMiembroADto(EmpresaMiembro empresa) {
        return modelMapper.map(empresa, EmpresaMiembroDto.class);
    }

    // Método para convertir una lista de entidades EmpresaMiembro a una lista de
    // EmpresaMiembroDto
    public List<EmpresaMiembroDto> convertirTodasLasEmpresasADto(List<EmpresaMiembro> empresas) {
        return empresas.stream()
                .map(this::convertirEmpresaMiembroADto)
                .collect(Collectors.toList());
    }
}
