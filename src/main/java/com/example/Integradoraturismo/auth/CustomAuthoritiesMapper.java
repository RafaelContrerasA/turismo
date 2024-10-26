package com.example.Integradoraturismo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.stereotype.Component;

import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuariosRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomAuthoritiesMapper implements GrantedAuthoritiesMapper {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(authorities);

        // Obtener el email del usuario autenticado desde las authorities
        Optional<? extends GrantedAuthority> oauth2Authority = authorities.stream()
                .filter(OAuth2UserAuthority.class::isInstance)
                .findFirst();
        
        if (oauth2Authority.isPresent()) {
            OAuth2UserAuthority oAuth2UserAuthority = (OAuth2UserAuthority) oauth2Authority.get();
            String email = oAuth2UserAuthority.getAttributes().get("email").toString();
            
            // Buscar el usuario en la base de datos usando su correo electrónico
            Optional<Usuario> optionalUsuario = usuariosRepository.findByEmail(email);

            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
                String roleName = usuario.getRol().getNombre();

                // Agregar el rol del usuario a las authorities
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
            }
        }

        return mappedAuthorities;
    }
}
