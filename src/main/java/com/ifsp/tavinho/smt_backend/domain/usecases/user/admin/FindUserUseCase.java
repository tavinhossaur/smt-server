package com.ifsp.tavinho.smt_backend.domain.usecases.user.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class FindUserUseCase implements UseCase<String, User> {

    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity<User> execute(String id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));
    }
    
}
