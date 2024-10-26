package com.example.Integradoraturismo.auth;

import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.repository.UsuariosRepository;
import com.example.Integradoraturismo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;




@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Obtener el usuario de OAuth2 predeterminado
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // Buscar el usuario en la base de datos
        String email = oAuth2User.getAttribute("email");
        Optional<Usuario> optionalUsuario = usuariosRepository.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        Usuario usuario = optionalUsuario.get();

        // Obtener el rol
        Long rolId = usuario.getRol().getId();
        Rol rol = rolRepository.findById(rolId).orElseThrow(() -> 
            new UsernameNotFoundException("Rol no encontrado"));

        // Obtener authorities del token OAuth2 (scopes) y agregar el rol personalizado
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()));

        // Crear un nuevo usuario OAuth2 con las authorities combinadas
        return new DefaultOAuth2User(
                authorities, // Combina authorities de OAuth2 con las personalizadas
                oAuth2User.getAttributes(),
                "email");
    }
}
