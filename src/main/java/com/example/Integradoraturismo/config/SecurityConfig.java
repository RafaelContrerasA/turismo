package com.example.Integradoraturismo.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.Integradoraturismo.auth.CustomAuthoritiesMapper;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    //Bean para model mapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Autowired
    private CustomAuthoritiesMapper customAuthoritiesMapper;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/stripe/**").permitAll() // Acceso libre a Stripe
                .requestMatchers("/api/reservaciones/**").permitAll() // Acceso libre a reservaciones
                .requestMatchers("/ruuta/**").hasRole("CLIENTE") // Solo clientes pueden acceder a productos
                .anyRequest().permitAll() // Acceso libre a otros endpoints
            )
            .formLogin(formLogin -> formLogin.defaultSuccessUrl("/logeado.html", true)) // Redirección personalizada tras login
            // .oauth2Login(oauth2 -> oauth2
            //     .userInfoEndpoint(userInfo -> 
            //         userInfo.userAuthoritiesMapper(customAuthoritiesMapper)
            //     )
            //     .successHandler(successHandler) // Manejo de éxito en OAuth2
            // )
            ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Permitir todos los orígenes
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permitir todos los headers
        configuration.setAllowCredentials(false); // Cambiar a `true` si necesitas cookies o credenciales
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
