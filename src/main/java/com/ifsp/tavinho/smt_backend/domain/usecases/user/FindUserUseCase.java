package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindUserUseCase implements UseCase<String, User> {

    private final UserRepository repository;

    @Override
    public User execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
}
