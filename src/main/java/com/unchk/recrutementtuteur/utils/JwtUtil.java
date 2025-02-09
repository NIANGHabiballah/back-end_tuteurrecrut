package com.unchk.recrutementtuteur.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Base64;
import java.security.Key;
import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    private final SecretKey SIGNING_KEY;

    @Autowired // ✅ Cette annotation assure que Spring injecte automatiquement la valeur de jwt.secret
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.SIGNING_KEY = Keys.hmacShaKeyFor(keyBytes); // Retourne un SecretKey
    }

    // 🔑 Générer un token
    public String generateToken(org.springframework.security.core.userdetails.UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire en 1 heure
                .signWith(SIGNING_KEY)
                .compact();
    }

    // Extraire le username du token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    //  Vérifier si le token est valide
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Vérifier si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 🏷️ Extraire les claims du token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(SIGNING_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
