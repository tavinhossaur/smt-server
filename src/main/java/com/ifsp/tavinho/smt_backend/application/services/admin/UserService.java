package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.CreateUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.UpdateUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.DeleteUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.FindUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.ListUsersUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CreateUserUseCase createUser;
    private final UpdateUserUseCase updateUser;
    private final DeleteUserUseCase deleteUser;
    private final FindUserUseCase findUser;
    private final ListUsersUseCase listUsers;

    public ResponseEntity<User> create(UserDTO input) {
        return this.createUser.execute(input);
    }

    public ResponseEntity<User> update(UserDTO input, String id) {
        return this.updateUser.execute(input, id);
    }

    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.deleteUser.execute(id);
    }

    public ResponseEntity<User> find(String id) {
        return this.findUser.execute(id);
    }

    public ResponseEntity<List<User>> list() {
        return this.listUsers.execute(null);
    }
    
}
