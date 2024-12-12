package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.RolRepository;
import com.example.Integradoraturismo.repository.UsuarioRepository;
import com.example.Integradoraturismo.repository.UsuariosRepository;
import com.example.Integradoraturismo.request.UsuarioEditarRequest;

import lombok.RequiredArgsConstructor;

import com.example.Integradoraturismo.auth.CustomUserDetails;
import com.example.Integradoraturismo.dto.UsuarioDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UsuariosRepository usuariosRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaMiembroService empresaMiembroService;
    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    public Usuario crearUsuario(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuario(int id) {
        return usuariosRepository.findById(id);
    }
    
    public Usuario obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
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
    

    
    public Usuario patchUsuario(Long id, UsuarioEditarRequest request) {
    return usuarioRepository.findById(id).map(usuario -> {
        // Actualizar los campos solo si no son null
        if (request.getEmail() != null) usuario.setEmail(request.getEmail());
        if (request.getNombre() != null) usuario.setNombre(request.getNombre());
        if (request.getPassword() != null) usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getTelefono() != null) usuario.setTelefono(request.getTelefono());
        if (request.getRolId() != null) {
            Rol rol = rolRepository.findById(request.getRolId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + request.getRolId()));
            usuario.setRol(rol);
        }
        if (request.getEmpresaId() != null) {
            EmpresaMiembro empresa = empresaMiembroService.obtenerEmpresaPorId(request.getEmpresaId());                    
            usuario.setEmpresaMiembro(empresa);
        }
        usuario.setEsStaff(request.isEsStaff());
        usuario.setRegistrado(request.isRegistrado());
        return usuariosRepository.save(usuario);
    }).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
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
        usuario.setRegistrado(true);
        usuario.setEsStaff(false);
        usuario.setEmpresaMiembro(null);
        return usuariosRepository.save(usuario);
    }

    public Optional<Usuario> encontrarPorEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public Usuario nuevoUsuarioInvitado(String email, String nombre) {
        Usuario invitado = new Usuario();
        invitado.setEmail(email);
        invitado.setRegistrado(false);
        invitado.setNombre(nombre);
        invitado.setTelefono("0000000000");

        String randomPassword = UUID.randomUUID().toString();
        invitado.setPassword(passwordEncoder.encode(randomPassword));

        Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente"); // Cambiar a buscar por nombre
        if (rolOptional.isPresent()) {
            invitado.setRol(rolOptional.get()); // Se le asigna rol de cliente a user que se logea en pantalla principal
                                                // por primera vez
        } else {
            throw new RuntimeException("El rol 'Cliente' no se encontró en la base de datos.");
        }

        return crearUsuario(invitado);
    }

    public Long obtenerIdUsuarioLogeado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No hay usuario autenticado");
        }

        String email = null;

        // Verifica si el usuario es de tipo OAuth2 o autenticación normal
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            // Caso OAuth2
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        } else if (authentication.getPrincipal() instanceof CustomUserDetails) {
            // Caso autenticación normal
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // En CustomUserDetails, `username` es el correo
        }

        if (email == null) {
            throw new IllegalStateException("No se pudo obtener el correo del usuario autenticado");
        }

        // Buscar al usuario en la base de datos
        Usuario usuario = usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado en la base de datos"));

        return usuario.getId();
    }
    
    public Long obtenerIdEmpresaDeUsuarioLogeado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
    
        String email = null;
    
        // Verifica si el usuario es de tipo OAuth2 o autenticación normal
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            // Caso OAuth2
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        } else if (authentication.getPrincipal() instanceof CustomUserDetails) {
            // Caso autenticación normal
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // En CustomUserDetails, `username` es el correo
        }
    
        if (email == null) {
            throw new IllegalStateException("No se pudo obtener el correo del usuario autenticado");
        }
    
        // Buscar al usuario en la base de datos
        Usuario usuario = usuariosRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("Usuario no encontrado en la base de datos"));
    
        // Verificar si el usuario tiene una empresa asociada
        if (usuario.getEmpresaMiembro() == null) {
            throw new IllegalStateException("El usuario no pertenece a ninguna empresa");
        }
    
        return usuario.getEmpresaMiembro().getId();
    }
    
        // Métodos para convertir a DTO
    public UsuarioDto convertirUsuarioADto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public List<UsuarioDto> convertirTodosLosUsuariosADto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .collect(Collectors.toList());
    }

}
