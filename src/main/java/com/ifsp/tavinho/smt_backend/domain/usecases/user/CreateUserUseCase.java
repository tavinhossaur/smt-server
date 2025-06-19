package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.services.AuthenticationService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements UseCase<UserDTO, User> {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<User> execute(UserDTO credentials) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(credentials));
    }
    
}
