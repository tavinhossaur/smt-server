package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Authorities;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase implements UseCase<UserDTO, User> {

    private final UserRepository repository;

    @Override
    public ResponseEntity<User> execute(UserDTO input, String id) {
        User existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (input.fullName() != null) existing.setFullName(input.fullName());
        if (input.email() != null) existing.setEmail(input.email());
        
        if (input.isAdmin() != null) {
            if (input.isAdmin() && !existing.getAuthoritiesList().contains(Authorities.ROLE_ADMIN_USER)) {
                existing.getAuthoritiesList().add(Authorities.ROLE_ADMIN_USER);
            } else if (!input.isAdmin()) {
                existing.getAuthoritiesList().remove(Authorities.ROLE_ADMIN_USER);
            }
        }

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<User> execute(UserDTO _unused) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
