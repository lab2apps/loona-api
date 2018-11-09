package com.loona.hachathon.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenUtil {

//    @Value("${application.secret}")
//    private String appSecret;

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String getUserToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Expired or invalid JWT token");
            return false;
        }

    }

    public String getUserId(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Expired or invalid JWT token");
            return null;
        }
    }
}
