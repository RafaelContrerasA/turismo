package com.example.Integradoraturismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.RolRepository;
import com.example.Integradoraturismo.repository.UsuarioRepository;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @PostMapping("/assign")
    public ResponseEntity<String> assignRolToUsuario(@RequestParam Long userId, @RequestParam Long rolId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Rol asignado correctamente");
    }
}
