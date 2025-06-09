package com.ifsp.tavinho.smt_backend.infra.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ApplicationProperties applicationProperties;

    public User register(UserDTO input) {
        String username = input.username();
        String email = input.email();
        String password = applicationProperties.getDefaultPassword();

        if (username.isBlank() || email.isBlank()) {
            throw new AppError("The user must have a username and an email.", HttpStatus.BAD_REQUEST);
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AppError("This email has already been taken.", HttpStatus.BAD_REQUEST);
        } 

        User user = new User(username, email, passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public User login(LoginCredentialsDTO input) {
        String email = input.email();
        String password = input.password();

        if (email.isBlank() || password.isBlank()) {
            throw new AppError("The login must provide an email and password.", HttpStatus.BAD_REQUEST);
        }
        
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new AppError("The email informed was not found.", HttpStatus.BAD_REQUEST);
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return userRepository.findByEmail(email).orElseThrow();
    }

}
