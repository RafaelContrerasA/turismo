package com.example.Integradoraturismo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave válida automáticamente

    // Genera el JWT, acepta claims adicionales
    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // Se asignan los claims personalizados
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(secretKey, SignatureAlgorithm.HS256) // Usar una clave válida
                .compact();
    }

    // Extrae el username (email) del token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extrae cualquier claim específico del token
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrae todos los claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}