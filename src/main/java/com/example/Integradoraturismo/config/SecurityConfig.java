package com.example.Integradoraturismo.config;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.Integradoraturismo.auth.CustomAuthoritiesMapper;
import com.example.Integradoraturismo.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro de JWT

    @Autowired
    private CustomAuthoritiesMapper customAuthoritiesMapper;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    // Bean para ModelMapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Configuración de AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/stripe/**").permitAll() // Acceso libre a Stripe
                .requestMatchers("/api/reservaciones/**").permitAll() // Acceso libre a reservaciones
                .requestMatchers("/ruuta/**").hasRole("CLIENTE") // Solo clientes pueden acceder a productos
                .anyRequest().permitAll() // Requiere autenticación para otros endpoints
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Página de inicio de sesión personalizada
                .defaultSuccessUrl("/index.html", true) // Redirección personalizada tras login
                .permitAll()
            )
            // .oauth2Login(oauth2 -> oauth2
            //     .userInfoEndpoint(userInfo -> 
            //         userInfo.userAuthoritiesMapper(customAuthoritiesMapper)
            //     )
            //     .successHandler(successHandler) // Manejo de éxito en OAuth2
            // )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Deshabilitar la creación de sesión
            ); // Agrega el filtro JWT

        return http.build();
    }

    // Configuración de PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
