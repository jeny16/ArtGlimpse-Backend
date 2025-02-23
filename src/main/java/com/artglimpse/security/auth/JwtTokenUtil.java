package com.artglimpse.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Component
public class JwtTokenUtil {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private long jwtExpirationMs;

    // private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 hours in ms

    private Key key;

    @PostConstruct
    public void init() {
        // Convert the plain-text secret to a Base64-encoded string
        String base64EncodedKey = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        // Use the Base64 encoded key to generate the HMAC key
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedKey));
    }

    // Retrieve username from jwt token (if needed)
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generate token for user using UserDetails instance and userId
    public String generateToken(UserDetails userDetails, String userId) {
        Map<String, Object> claims = new HashMap<>();
        // Additional claims (such as email) can be added here if needed.
        return doGenerateToken(claims, userId);
    }

    // Create the token with subject as userId
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // subject now is the MongoDB userId
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}
