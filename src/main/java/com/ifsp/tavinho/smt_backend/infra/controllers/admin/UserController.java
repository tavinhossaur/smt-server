package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.application.services.UserService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_USERS)
public class UserController implements EntityController<UserDTO, User> {
    
    private final UserService userService;

    @Override
    public ResponseEntity<User> create(@Valid UserDTO input) {
        return this.userService.create(input);
    }

    @Override
    public ResponseEntity<User> update(UserDTO input, String id) {
        return this.userService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.userService.delete(id);
    }

    @Override
    public ResponseEntity<User> find(String id) {
        return this.userService.find(id);
    }

    @Override
    public ResponseEntity<List<User>> list() {
        return this.userService.list();
    }

}
