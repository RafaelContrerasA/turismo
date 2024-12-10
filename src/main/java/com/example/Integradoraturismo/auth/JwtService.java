package com.example.Integradoraturismo.auth;

import com.example.Integradoraturismo.util.JwtUtil;

import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtService {

    private final JwtUtil jwtUtil; // Inyecta JwtUtil

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil; // Constructor para inyectar JwtUtil
    }

    public String generateToken(String email, String role) {
        // Aquí, en vez de crear el token manualmente, delegas la creación a JwtUtil
        Map<String, Object> claims = Map.of("role", role); // Puedes agregar más claims si lo deseas
        return jwtUtil.generateToken(email, claims); // Delegamos la creación del token
    }

    public String extractEmail(String token) {
        return jwtUtil.extractUsername(token); // Delegamos la extracción del nombre de usuario
    }

    public String extractRole(String token) {
        // Usamos el método de JwtUtil para obtener los claims del token
        return jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean validateToken(String token, String userDetailsEmail) {
        final String email = extractEmail(token);
        return email.equals(userDetailsEmail) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return jwtUtil.extractClaim(token, Claims::getExpiration).before(new java.util.Date());
    }
}
