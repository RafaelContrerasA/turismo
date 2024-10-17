package com.example.Integradoraturismo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/empresa/**").hasRole("EMPRESA")
                .requestMatchers("/user/**").hasRole("USUARIO")
                .anyRequest().authenticated()
            )
            .formLogin().permitAll() // Configurar el login form
            .and()
            .logout().permitAll();
        
        return http.build();
    }
}
