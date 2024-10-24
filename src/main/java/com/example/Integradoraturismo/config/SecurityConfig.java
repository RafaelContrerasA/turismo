package com.example.Integradoraturismo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationSuccessHandler successHandler;

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para facilitar las pruebas
                .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/stripe/**").permitAll() // Permitir acceso sin autenticación a Stripe
                .requestMatchers("/api/reservaciones/**").permitAll() // Permitir a usuarios no autenticados crear reservaciones
                .requestMatchers("/productos/**").authenticated() // Solo autenticados pueden acceder a /productos
                .anyRequest().permitAll() // Permitir el acceso al resto de endpoints
                )
                .oauth2Login(login -> login.successHandler(successHandler)); // Habilitar autenticación con OAuth2

        return http.build();
    }

}
