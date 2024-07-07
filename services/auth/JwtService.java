package com.kstoi.web_chess.services.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtService {
    private final long expiration = 8640000;
    private final String prefix = "Bearer";
    private final SecretKey key = Keys.hmacShaKeyFor(
            Base64.getEncoder().encode("v3r1S3ckretKwiwi!!!jbiadjbdasjbsadbjkjkkjblSANDFJKSDFJAas".getBytes(StandardCharsets.UTF_8))
    );
    public String getToken(String username){
        return Jwts.builder()
                .subject(username)
                .expiration(
                        new Date(System.currentTimeMillis()+expiration)
                )
                .signWith(key)
                .compact();
    }
    public String getUser(HttpServletRequest request) throws NullPointerException {
        String token = request.getHeader("Authorization");
        if(token != null){
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token.replace(prefix,""))
                    .getPayload()
                    .getSubject();
        }
        throw new NullPointerException();
    }
}
