package com.app.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class tokenService {
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final byte[] SECRET_KEY = secretKey.getEncoded();

    public String generateToken(int accountId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);
        return Jwts.builder()
                .setSubject(Integer.toString(accountId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String validateTokenAndGetAccountId(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            Integer accountId = Integer.parseInt(claims.getBody().getSubject());
            return String.valueOf(accountId);
        } catch (Exception ex) {
            return null;
        }
    }

}
