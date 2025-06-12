package com.ifsp.tavinho.smt_backend.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApplicationProperties {
    
    @Value("${system.user.default-password}")
    private String defaultPassword;

    @Value("${security.jwt.expiration-time}")
    private String jwtExpirationTime;
    
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    public final static String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public final static String TIMEZONE = "America/Sao_Paulo";

}
