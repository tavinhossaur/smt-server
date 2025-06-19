package com.ifsp.tavinho.smt_backend.application.interactors.authentication;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements UseCase<LoginCredentialsDTO, LoginResponseDTO> {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO execute(LoginCredentialsDTO input) {
        String email = input.email();
        String password = input.password();

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
