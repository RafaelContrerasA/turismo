package com.example.Integradoraturismo.service;


import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuariosRepository;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
        
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private final UsuariosRepository usuariosRepository;

    public UsuarioService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuario(int id) {
        return usuariosRepository.findById(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuario actualizarUsuario(int id, Usuario detallesUsuario) {
        return usuariosRepository.findById(id).map(usuario -> {
            usuario.setNombre(detallesUsuario.getNombre());
            usuario.setEmail(detallesUsuario.getEmail());
            usuario.setTelefono(detallesUsuario.getTelefono());
            usuario.setEsStaff(detallesUsuario.getEsStaff());
            return usuariosRepository.save(usuario);
        }).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public void eliminarUsuario(int id) {
        usuariosRepository.deleteById(id);
    }
    
    public Usuario registrarUsuario(String nombre, String email, String telefono, String password, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
        usuario.setRol(rol);
        return usuariosRepository.save(usuario);
    }

    public Optional<Usuario> encontrarPorEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }
}

