package com.ifsp.tavinho.smt_backend.shared;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@NoArgsConstructor
public final class ApplicationProperties {
    
    @Value("${system.user.default-password}")
    private String defaultPassword;

    @Value("${security.jwt.expiration-time}")
    private String jwtExpirationTime;
    
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String TIMEZONE = "America/Sao_Paulo";

}
