package com.ifsp.tavinho.smt_backend.application.services.guest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.interactors.authentication.LoginUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final LoginUseCase loginUseCase;
    private final UserRepository userRepository;

    public LoginResponseDTO login(LoginCredentialsDTO credentials) {
        String email = credentials.email();

        if (this.userRepository.findByEmail(email).isEmpty()) {
            throw new AppError("The email informed was not found.", HttpStatus.BAD_REQUEST);
        }

        return this.loginUseCase.execute(credentials);
    }

}
