package com.enigma.loan.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    // kita panggil informasi secret sign dan expired time
    @Value("${app.jwt-expiration-ms}")
    private int jwtExpirationMs;

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    // 1. generate jwt
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // extract roles dari UserDetails
        String role = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("User tidak memiliki role"));

        // proses pembuatan jwt
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date().getTime() + jwtExpirationMs)))
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    // 2. validasi token
    public boolean validateJwtToken(String token) {
        try {
            // hadirkan tukang verify token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();

            // verify token
            verifier.verify(token);

            return true;
        } catch (TokenExpiredException e){
            throw new TokenExpiredException("silahkan login kembali", e.getExpiredOn());
        } catch (JWTVerificationException e) {
            log.error("JSON Web Token tidak valid : " + e.getMessage());
            return false;
        }
    }

    // 3. get username
    public String getUsernameFromJwtToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    // 4. get role
    public String getRoleFromJwtToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("role").toString();
    }

}
