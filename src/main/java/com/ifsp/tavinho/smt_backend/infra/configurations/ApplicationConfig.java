package com.ifsp.tavinho.smt_backend.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    UserDetailsService userDetailsService() {
        return email -> this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
