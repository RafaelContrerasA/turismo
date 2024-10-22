package com.example.Integradoraturismo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Integradoraturismo.auth.CustomAuthoritiesMapper;
import com.example.Integradoraturismo.auth.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomAuthoritiesMapper customAuthoritiesMapper;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests(authz -> authz
                //Existe 1. ROLE_CLIENTE 2.ROLE_EMPRESA 3.ROLE_ADMIN
                .requestMatchers("/productos/**").hasRole("CLIENTE") // Solo rol CLIENTE puede acceder
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> 
                    userInfo.userService(customOAuth2UserService) // Usar CustomOAuth2UserService
                        .userAuthoritiesMapper(customAuthoritiesMapper) // Agregar AuthoritiesMapper si es necesario
                )
                .successHandler(successHandler) // Manejar redirección después de autenticación
            )
            .exceptionHandling(exception -> 
                exception.accessDeniedPage("/rol_inadecuado")
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

}
