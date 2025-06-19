package com.ifsp.tavinho.smt_backend.application.interactors.authentication;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements UseCase<LoginCredentialsDTO, ServerApiResponse<LoginResponseDTO>> {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<ServerApiResponse<LoginResponseDTO>> execute(LoginCredentialsDTO input) {
        String email = input.email();
        String password = input.password();
        
        if (!this.userRepository.findByEmail(email).isPresent()) {
            throw new AppError("The email informed was not found.", HttpStatus.BAD_REQUEST);
        }

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = this.userRepository.findByEmail(email).orElseThrow();

        return ResponseEntity.ok(
            ServerApiResponse.<LoginResponseDTO>builder()
                .status(Status.SUCCESS)
                .message("User logged in successfully.")
                .data(this.createAuthenticationResponse(user)
            ).build()
        );
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
