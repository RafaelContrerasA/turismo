package com.example.Integradoraturismo.auth;

import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuariosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;




@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuariosRepository usuariosRepository;

    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
                
        String redirectUrl = "/";
        String email = "uu";
        String nombre = "ueu";
        
        if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
            nombre = oauthUser.getAttribute("name");
        }
       

        Optional<Usuario> usuarioOptional = usuariosRepository.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            // Si el usuario no está en la base de datos, lo guardamos
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setTelefono(""); // No se obtiene directamente el teléfono de Google OAuth
            nuevoUsuario.setEsStaff(false);  // Los usuarios no serán staff por defecto
            usuariosRepository.save(nuevoUsuario);
        }

        // Redirigir a la página principal
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
