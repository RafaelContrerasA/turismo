package com.example.Integradoraturismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.request.UsuarioEditarRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.UsuarioService;
import com.example.Integradoraturismo.dto.UsuarioDto;
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
    public ResponseEntity<ApiResponse> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            List<UsuarioDto> usuarioDtos = usuarioService.convertirTodosLosUsuariosADto(usuarios);
            return ResponseEntity.ok(new ApiResponse("success", usuarioDtos));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }

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
    
    //@PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/usuarios/mis-datos")
    public ResponseEntity<ApiResponse> getUsuarioBySesion() {
        try {
            Long id = usuarioService.obtenerIdUsuarioLogeado();
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            UsuarioDto usuarioDto = usuarioService.convertirUsuarioADto(usuario);
            return ResponseEntity.ok(new ApiResponse("success", usuarioDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, @RequestBody Usuario usuarioUpdate) {
        Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuarioUpdate);
        return ResponseEntity.ok(updatedUsuario);
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<ApiResponse> patchUsuario(@PathVariable Long id,
            @RequestBody UsuarioEditarRequest usuarioUpdate) {
        try {
            Usuario updatedUsuario = usuarioService.patchUsuario(id, usuarioUpdate);
            UsuarioDto updatedUserDto = usuarioService.convertirUsuarioADto(updatedUsuario);

            return ResponseEntity.ok(new ApiResponse("success", updatedUserDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));

        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
