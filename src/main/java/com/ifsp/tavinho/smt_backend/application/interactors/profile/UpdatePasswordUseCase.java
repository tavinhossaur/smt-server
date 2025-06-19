package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase implements UseCase<UpdatePasswordDTO, User> {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(UpdatePasswordDTO input, User user) {
        String currentPassword = input.currentPassword();
        String newPassword = input.newPassword();

        if (!this.passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AppError("Current password is incorrect.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));

        return this.repository.save(user);
    }

    @Override
    public User execute(UpdatePasswordDTO _unused) {
        throw new UnsupportedOperationException("An existing user is required for updating the password.");
    }

}
