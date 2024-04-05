package com.example.EmplyeeManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    private static final int expireInMs = 300 * 1000;
    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generate(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuer("test.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
                .signWith(key)
                .compact();
    }
    public boolean validate(String token){
        if(getEmail(token) != null && isExpired(token)){
            return true;
        }
        return false;
    }
    public String getEmail(String token){
        return getClaims(token).getSubject();
    }
    public boolean isExpired(String token){
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }
    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
