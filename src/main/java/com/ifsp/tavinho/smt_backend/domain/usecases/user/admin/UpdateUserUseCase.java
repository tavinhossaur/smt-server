package com.ifsp.tavinho.smt_backend.domain.usecases.user.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class UpdateUserUseCase implements UseCase<UserDTO, User> {

    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity<User> execute(UserDTO input, String id) {
        User existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (input.username() != null) existing.setUsername(input.username());
        if (input.email() != null) existing.setEmail(input.email());
        if (input.isAdmin() != null) existing.setIsAdmin(input.isAdmin());

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<User> execute(UserDTO _unused) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
