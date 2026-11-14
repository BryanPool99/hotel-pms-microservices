package com.bryandev.identity_service.infrastructure.in.security;

import com.bryandev.identity_service.application.out.TokenGeneratorPort;
import com.bryandev.identity_service.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenAdapter implements TokenGeneratorPort {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(User user) {
        var dateNow = new Date();
        var expiryDate = new Date(dateNow.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(dateNow)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
}
