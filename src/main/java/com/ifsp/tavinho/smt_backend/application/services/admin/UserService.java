package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Authorities;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties applicationProperties;

    public User create(UserDTO input) {
        String fullName = input.fullName();
        String email = input.email();
        String password = applicationProperties.getDefaultPassword();
        String enrollment = input.enrollment();
        Boolean isAdmin = input.isAdmin();

        if (this.repository.findByEmail(email).isPresent()) {
            throw new AppError("This email has already been taken.", HttpStatus.BAD_REQUEST);
        } 

        User user = new User(fullName, email, enrollment, this.passwordEncoder.encode(password));

        user.getAuthoritiesList().add(Authorities.ROLE_DEFAULT_USER);

        if (isAdmin != null && isAdmin) user.getAuthoritiesList().add(Authorities.ROLE_ADMIN_USER);

        return this.repository.save(user);
    }

    public User update(UserDTO input, String id) {
        User existing = this.find(id);
        
        if (input.fullName() != null) existing.setFullName(input.fullName());
        if (input.email() != null) existing.setEmail(input.email());
        if (input.enrollment() != null) existing.setEnrollment(input.enrollment());
        
        if (input.isAdmin() != null) {
            if (input.isAdmin() && !existing.getAuthoritiesList().contains(Authorities.ROLE_ADMIN_USER)) {
                existing.getAuthoritiesList().add(Authorities.ROLE_ADMIN_USER);
            } else if (!input.isAdmin()) {
                existing.getAuthoritiesList().remove(Authorities.ROLE_ADMIN_USER);
            }
        }

        return this.repository.save(existing);
    }

    public Boolean delete(String id) {
        User user = this.find(id);

        this.repository.delete(user);

        return true;
    }

    public User find(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public List<User> list() {
        return this.repository.findAllByOrderByFullNameAsc();
    }
    
}
