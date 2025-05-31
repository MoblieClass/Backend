package com.wj2025.mobileclass.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    // 生成 JWT Token
    public String generateToken(String username) {
        var SIGNING_KEY = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    // 解析JWT获取用户名
    public String getUsernameFromToken(String token) {
        var SIGNING_KEY = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 校验token是否有效
    public boolean validateToken(String token) {
        var SIGNING_KEY = Keys.hmacShaKeyFor(secret.getBytes());
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
