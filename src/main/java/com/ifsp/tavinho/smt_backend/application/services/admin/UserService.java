package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.CreateUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.UpdateUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.DeleteUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.FindUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.ListUsersUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CreateUserUseCase createUser;
    private final UpdateUserUseCase updateUser;
    private final DeleteUserUseCase deleteUser;
    private final FindUserUseCase findUser;
    private final ListUsersUseCase listUsers;

    public User create(UserDTO input) {
        return this.createUser.execute(input);
    }

    public User update(UserDTO input, String id) {
        User user = this.findUser.execute(id);
        return this.updateUser.execute(input, user);
    }

    public Boolean delete(String id) {
        User user = this.findUser.execute(id);
        return this.deleteUser.execute(user);
    }

    public User find(String id) {
        return this.findUser.execute(id);
    }

    public User findByEmail(String email) {
        return this.findUser.execute(email, "");
    }

    public List<User> list() {
        return this.listUsers.execute(null);
    }
    
}
