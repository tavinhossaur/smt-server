package com.ifsp.tavinho.smt_backend.infra.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Authorities;
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
        String fullName = input.fullName();
        String email = input.email();
        String password = applicationProperties.getDefaultPassword();
        Boolean isAdmin = input.isAdmin();

        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new AppError("This email has already been taken.", HttpStatus.BAD_REQUEST);
        } 

        User user = new User(fullName, email, this.passwordEncoder.encode(password));

        user.getAuthoritiesList().add(Authorities.ROLE_DEFAULT_USER);

        if (isAdmin != null && isAdmin) user.getAuthoritiesList().add(Authorities.ROLE_ADMIN_USER);

        return this.userRepository.save(user);
    }

    public User login(LoginCredentialsDTO input) {
        String email = input.email();
        String password = input.password();
        
        if (!this.userRepository.findByEmail(email).isPresent()) {
            throw new AppError("The email informed was not found.", HttpStatus.BAD_REQUEST);
        }

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return this.userRepository.findByEmail(email).orElseThrow();
    }

}
