package org.example.jvspringbootfirstbook.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwtUtil.expiration}")
    private long expiration;

    private Key secret;

    public JwtUtil(@Value("${jwtUtil.secret}") String secretString) {
        secret = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();
    }

    public boolean isValid(String tokenFromRequest) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(tokenFromRequest);

        return !claimsJws.getBody()
                .getExpiration()
                .before(new Date());
    }

    public String getUserName(String tokenFromRequest) {
        return getClaim(tokenFromRequest, Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims body = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(body);
    }
}
