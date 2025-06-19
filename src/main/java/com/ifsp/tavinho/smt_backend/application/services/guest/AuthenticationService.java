package com.ifsp.tavinho.smt_backend.application.services.guest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.interactors.authentication.LoginUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final LoginUseCase loginUseCase;

    public ResponseEntity<ServerApiResponse<LoginResponseDTO>> login(LoginCredentialsDTO credentials) { 
        return this.loginUseCase.execute(credentials);
    }

}
