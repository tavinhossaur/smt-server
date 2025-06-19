package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements UseCase<User, Boolean> {

    private final UserRepository repository;

    @Override
    public Boolean execute(User user) {
        this.repository.delete(user);
        return true;
    }
    
}
