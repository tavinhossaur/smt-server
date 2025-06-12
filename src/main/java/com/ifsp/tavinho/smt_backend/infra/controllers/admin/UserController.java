package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.CreateUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.DeleteUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.FindUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.ListUsersUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.UpdateUserUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_USERS;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_USERS)
public class UserController implements CrudOperations<UserDTO, User> {
    
    private final CreateUserUseCase createUser;
    private final UpdateUserUseCase updateUser;
    private final DeleteUserUseCase deleteUser;
    private final FindUserUseCase findUser;
    private final ListUsersUseCase listUsers;

    @Override
    public ResponseEntity<User> create(@Valid UserDTO input) {
        return this.createUser.execute(input);
    }

    @Override
    public ResponseEntity<User> update(UserDTO input, String id) {
        return this.updateUser.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.deleteUser.execute(id);
    }

    @Override
    public ResponseEntity<User> find(String id) {
        return this.findUser.execute(id);
    }

    @Override
    public ResponseEntity<List<User>> list() {
        return this.listUsers.execute(null);
    }

}
