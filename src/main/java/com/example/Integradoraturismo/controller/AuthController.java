package com.example.Integradoraturismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Integradoraturismo.auth.CustomUserDetails;
import com.example.Integradoraturismo.auth.JwtService;
import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.RolRepository;
import com.example.Integradoraturismo.request.LoginRequest;
import com.example.Integradoraturismo.request.RegistroUsuarioRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;
    
    
    
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Realizar el logout
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);
        }
        // Redirigir a /logout.html
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/logout.html").build();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistroUsuarioRequest request) {
        Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente");
        if (rolOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse ("El rol Cliente no existe.", null));
        }

        Usuario usuario = usuarioService.registrarUsuario(
            request.getNombre(),
            request.getEmail(),
            request.getTelefono(),
            request.getPassword(),
            rolOptional.get()
        );
        return ResponseEntity.ok(new ApiResponse("Usuario registrado con éxito.", null));
    }
    
@PostMapping("/loginUser")
public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar JWT
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        String role = usuario.getRol().getNombre();
        String token = jwtService.generateToken(request.getEmail(), role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(new ApiResponse("jwt", token));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
    }
}

    
    
    
}
