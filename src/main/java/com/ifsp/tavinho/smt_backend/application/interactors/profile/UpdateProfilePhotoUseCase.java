package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProfilePhotoUseCase implements UseCase<String, User> {

    private final UserRepository repository;

    @Override
    public User execute(String encodedBase64Image, User user) {
        user.setProfilePhoto(encodedBase64Image);
        return this.repository.save(user);
    }

    @Override
    public User execute(String _unused) {
        throw new UnsupportedOperationException("An existing user is required for updating the profile photo.");
    }
    
}
