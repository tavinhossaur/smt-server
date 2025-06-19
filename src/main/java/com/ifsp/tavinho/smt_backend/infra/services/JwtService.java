package com.ifsp.tavinho.smt_backend.infra.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final ApplicationProperties applicationProperties;

    public String extractEmail(String token) {
        return this.extractClaim(token, DecodedJWT::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        
        if (userDetails instanceof User user) {
            extraClaims.put("id", user.getId());
            extraClaims.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        }
        
        return this.generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, this.getExpirationTime());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = this.extractEmail(token);
        return (email != null && email.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
    }
    
    public long getExpirationTime() {
        return Long.parseLong(this.applicationProperties.getJwtExpirationTime());
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return JWT.create()
                .withPayload(extraClaims)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(this.applicationProperties.getJwtSecretKey()));
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return this.extractClaim(token, DecodedJWT::getExpiresAt);
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