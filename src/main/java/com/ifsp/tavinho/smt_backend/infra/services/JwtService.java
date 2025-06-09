package com.ifsp.tavinho.smt_backend.infra.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.shared.utils.LoggerUtil;

@Service
public class JwtService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public String extractUsername(String token) {
        return extractClaim(token, DecodedJWT::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        
        if (userDetails instanceof User user) extraClaims.put("isAdmin", user.getIsAdmin());
        
        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, this.getExpirationTime());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    public long getExpirationTime() {
        return Long.parseLong(applicationProperties.getJwtExpirationTime());
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return JWT.create()
                .withPayload(extraClaims)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(applicationProperties.getJwtSecretKey()));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, DecodedJWT::getExpiresAt);
    }

    private <T> T extractClaim(String token, Function<DecodedJWT, T> claimsResolver) {
        try {
            final DecodedJWT jwt = JWT.decode(token);
            return claimsResolver.apply(jwt);
        } catch (JWTDecodeException e) {
            LoggerUtil.error("Error decoding JWT: " + e.getMessage());
            throw new AppError("Error decoding JWT: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}