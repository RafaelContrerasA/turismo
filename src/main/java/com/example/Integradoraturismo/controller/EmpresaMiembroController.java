package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.dto.EmpresaMiembroDto;
import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.request.EmpresaMiembroCreateRequest;
import com.example.Integradoraturismo.request.EmpresaMiembroPatchRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.EmpresaMiembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/empresa-miembro")
public class EmpresaMiembroController {

    private final EmpresaMiembroService empresaMiembroService;

    // Obtener todas las empresas miembros
    @GetMapping
    public List<EmpresaMiembro> obtenerTodasLasEmpresas() {
        return empresaMiembroService.obtenerTodasLasEmpresas();
    }

    // Obtener una empresa miembro por su id
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaMiembro> obtenerEmpresaPorId(@PathVariable Long id) {
        return new ResponseEntity<>(empresaMiembroService.obtenerEmpresaPorId(id), HttpStatus.OK);
    }

    // Crear una nueva empresa miembro
    @PostMapping
    public ResponseEntity<ApiResponse> crearEmpresa(@RequestBody EmpresaMiembroCreateRequest empresaMiembro) {
        try {
            EmpresaMiembro empresaCreada = empresaMiembroService.crearEmpresa(empresaMiembro);
            EmpresaMiembroDto empresaCreadaDto = empresaMiembroService.convertirEmpresaMiembroADto(empresaCreada);
            return ResponseEntity.ok(new ApiResponse("success", empresaCreadaDto));
            
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
        
    }
    
    // Actualizar una empresa miembro parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> actualizarEmpresaParcialmente(@PathVariable Long id, @RequestBody EmpresaMiembroPatchRequest request) {
        try {
            EmpresaMiembro empresaActualizada = empresaMiembroService.actualizarEmpresaParcialmente(id, request);
            EmpresaMiembroDto empresaActualizadaDto = empresaMiembroService.convertirEmpresaMiembroADto(empresaActualizada);
            return ResponseEntity.ok(new ApiResponse("success", empresaActualizadaDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
        
    }


    // Actualizar una empresa miembro
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaMiembro> actualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaMiembro detallesEmpresa) {
        EmpresaMiembro empresaActualizada = empresaMiembroService.actualizarEmpresa(id, detallesEmpresa);
        return new ResponseEntity<>(empresaActualizada, HttpStatus.OK);
    }

    // Eliminar una empresa miembro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaMiembroService.eliminarEmpresa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
