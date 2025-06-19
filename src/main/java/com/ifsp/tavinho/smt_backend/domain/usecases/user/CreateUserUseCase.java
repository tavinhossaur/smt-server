package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.enums.Authorities;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements UseCase<UserDTO, User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties applicationProperties;

    @Override
    public ResponseEntity<User> execute(UserDTO input) {
        String fullName = input.fullName();
        String email = input.email();
        String password = applicationProperties.getDefaultPassword();
        Boolean isAdmin = input.isAdmin();

        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new AppError("This email has already been taken.", HttpStatus.BAD_REQUEST);
        } 

        User user = new User(fullName, email, this.passwordEncoder.encode(password));

        user.getAuthoritiesList().add(Authorities.ROLE_DEFAULT_USER);

        if (isAdmin != null && isAdmin) user.getAuthoritiesList().add(Authorities.ROLE_ADMIN_USER);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(user));
    }
    
}
