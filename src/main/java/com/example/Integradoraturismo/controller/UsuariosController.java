package com.example.Integradoraturismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.service.UsuarioService;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping("/usuarios")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        Usuario usuario = usuarioService.obtenerUsuario(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no se encuentra."));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, @RequestBody Usuario usuarioUpdate) {
        Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuarioUpdate);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
