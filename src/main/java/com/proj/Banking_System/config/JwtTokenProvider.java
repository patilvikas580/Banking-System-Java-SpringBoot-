package com.proj.Banking_System.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration}")
    private String jwtExpiration;

    public String generateToken(Authentication authentication) {
        String userName=authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpiration));

        return Jwts.builder().setSubject(userName)
                .setIssuedAt(currentDate).setExpiration(expiryDate).signWith(key()).compact();

    }
    private Key key(){
        byte[] bytes= Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
