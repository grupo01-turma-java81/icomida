package com.generation.icomida.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    public static final String SECRET = "TYl11v+QRGmI33qighTEDlla9TrBy33MzoD6qlwHyb3buJIWRPj09SdELGBT5o9oM8eT5BFfz7BgcXVGRg0Ra27LgBweIOs8IVZoEy9g4YB7h69bIYhTWoo4QwRdg7SZcAaUuNLvFp+NQVOQD9Y3dXtnOnjGurgPvqAaYgVnX3ZiC+YJ0Kj5ZewkYb14KlPXROeVxNxoKYEP3CTwcHmKcvMSbHVh7IKhqbByna3nON7zrOYxP6fDTu8STXoebY5mDjpkvtcAJ4ziyDIBbZQC8+TJwaTBhU0z6kRPVnD5Lejz0zbu6DeuwQarkhWGOqWzyhQrMwbKQAaDUao0no3lCihoPSibKg/hXUqLW4ruv6A=";

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }
}