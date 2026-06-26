package com.ocean.demoJSPJwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Use a fixed secret key encoded in Base64 (must be at least 256 bits for HS256)
    // In production, store this in environment variables or a secure config
    private static final String SECRET = "MySuperSecretKeyForJWTTokenGeneration2026DemoJSPProjectJWTDemo";
    private SecretKey key;

    // Token validity: 24 hours
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        // Generate HMAC-SHA key from the secret string
        byte[] keyBytes = SECRET.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
        logger.info("JwtUtil initialized with HMAC-SHA key");
    }

    /**
     * Generate a JWT token for the given username.
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_MS);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        logger.debug("Generated JWT token for user: {}", username);
        return token;
    }

    /**
     * Validate a JWT token.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extract username from a JWT token.
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
