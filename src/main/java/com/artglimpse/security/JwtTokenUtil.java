package com.artglimpse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.artglimpse.security.CustomUserDetails;
import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream()
                .findFirst().orElseThrow(() -> new RuntimeException("User has no roles"))
                .getAuthority()); // Store user role
        return doGenerateToken(claims, userId);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userIdFromToken = getUsernameFromToken(token);
        // Cast to CustomUserDetails to access the id field.
        if (!(userDetails instanceof CustomUserDetails)) {
            return false;
        }
        CustomUserDetails customUser = (CustomUserDetails) userDetails;
        return (userIdFromToken.equals(customUser.getId()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}