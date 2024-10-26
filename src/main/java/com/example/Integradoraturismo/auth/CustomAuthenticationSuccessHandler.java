package com.example.Integradoraturismo.auth;

import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.repository.UsuariosRepository;
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

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolRepository rolRepository;  // Agregamos el RolRepository para consultar los roles

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl = "/";
        String email = "";
        String nombre = "";

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
            nombre = oauthUser.getAttribute("name");
            
            
            //Si es la primera vez que ingresa un usuario lo registra en la base de datos
            Optional<Usuario> usuarioOptional = usuariosRepository.findByEmail(email);
            if (usuarioOptional.isEmpty()) {
                // Si el usuario no está en la base de datos, lo guardamos
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setEmail(email);
                nuevoUsuario.setTelefono("empty"); // No se obtiene directamente el teléfono de Google OAuth
                nuevoUsuario.setEsStaff(false);  // Los usuarios no serán staff por defecto
                
                //Dar de alta con contraseña al azar que podra cambiar despues en su perfil
                String randomPassword = UUID.randomUUID().toString();
                nuevoUsuario.setPassword(passwordEncoder.encode(randomPassword));

                // Buscar el rol con nombre "Cliente"
                Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente");  // Cambiar a buscar por nombre
                if (rolOptional.isPresent()) {
                    nuevoUsuario.setRol(rolOptional.get()); //Se le asigna rol de cliente a user que se logea en pantalla principal por primera vez
                } else {
                    throw new RuntimeException("El rol 'Cliente' no se encontró en la base de datos.");
                }

                usuariosRepository.save(nuevoUsuario);
                
                //Despues de registrarse se cierra su sesion y se manda a la pantalla de que su registro fue exitoso
                SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
                logoutHandler.logout(request, response, authentication);
                redirectUrl = "/registered.html";

            }
        }
        // Redirigir a la página principal
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
        
    }
}
