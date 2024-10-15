package com.example.Integradoraturismo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/productos/**").authenticated()  // Solo autenticados pueden acceder a /productos
                                .anyRequest().permitAll()  // Permitir el acceso al resto
                )
                .httpBasic(withDefaults())  // Continuar con la configuración
                .csrf(csrf -> csrf.disable());  // Desactivar CSRF

        return http.build();
    }
}





