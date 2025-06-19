package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListUsersUseCase implements UseCase<Void, List<User>> {

    private final UserRepository repository;

    @Override
    public ResponseEntity<List<User>> execute(Void v) {
        return ResponseEntity.ok(this.repository.findAll());
    }
    
}
