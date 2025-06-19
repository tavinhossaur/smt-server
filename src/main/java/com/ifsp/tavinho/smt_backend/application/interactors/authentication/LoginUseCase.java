package com.ifsp.tavinho.smt_backend.application.interactors.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.services.AuthenticationService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements UseCase<LoginCredentialsDTO, ServerApiResponse<LoginResponseDTO>> {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<ServerApiResponse<LoginResponseDTO>> execute(LoginCredentialsDTO credentials) { 
        return ResponseEntity.ok(
            ServerApiResponse.<LoginResponseDTO>builder()
                .status(Status.SUCCESS)
                .message("User logged in successfully.")
                .data(authenticationService.login(credentials)
            ).build()
        );
    }

}
