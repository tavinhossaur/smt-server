package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginCredentialsDTO credentials) {
        String email = credentials.email();
        String password = credentials.password();

        if (this.userRepository.findByEmail(email).isEmpty()) {
            throw new AppError("The email informed was not found.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return this.createAuthenticationResponse((User) authentication.getPrincipal());
    }

    private LoginResponseDTO createAuthenticationResponse(User user) {
        return new LoginResponseDTO(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
            this.jwtService.generateToken(user), 
            this.jwtService.getExpirationTime()
        );
    }

}
