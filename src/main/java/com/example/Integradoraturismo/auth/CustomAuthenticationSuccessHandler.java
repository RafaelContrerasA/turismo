package com.example.Integradoraturismo.auth;

import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.repository.UsuariosRepository;
import com.example.Integradoraturismo.util.JwtUtil;
import com.example.Integradoraturismo.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private final UsuariosRepository usuariosRepository;

    @Autowired
    private final RolRepository rolRepository;  // Agregamos el RolRepository para consultar los roles
    
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

                String email = "";
                String nombre = "";
                Usuario usuario;

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
            nombre = oauthUser.getAttribute("name");

            // Si el usuario no existe, se registra en la base de datos
            Optional<Usuario> usuarioOptional = usuariosRepository.findByEmail(email);
            if (usuarioOptional.isEmpty()) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setEmail(email);
                nuevoUsuario.setTelefono("empty");
                nuevoUsuario.setEsStaff(false);

                // Contraseña aleatoria
                String randomPassword = UUID.randomUUID().toString();
                nuevoUsuario.setPassword(randomPassword);

                // Asignar rol "Cliente"
                Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente");
                if (rolOptional.isPresent()) {
                    nuevoUsuario.setRol(rolOptional.get());
                } else {
                    throw new RuntimeException("El rol 'Cliente' no se encontró en la base de datos.");
                }

                usuario = usuariosRepository.save(nuevoUsuario);
            } else {
                usuario = usuarioOptional.get();
            }

            // Generar JWT
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", usuario.getRol().getNombre());
            String jwt = jwtUtil.generateToken(usuario.getEmail(), claims);
            
            

            // Responder con el JWT
            response.setContentType("application/json");
            response.getWriter().write("{\"jwt\":\"" + jwt + "\"}");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        
    }
}



