package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.models.EmpresaMiembro;
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
    public ResponseEntity<EmpresaMiembro> crearEmpresa(@RequestBody EmpresaMiembro empresaMiembro) {
        EmpresaMiembro empresaCreada = empresaMiembroService.crearEmpresa(empresaMiembro);
        return new ResponseEntity<>(empresaCreada, HttpStatus.CREATED);
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
