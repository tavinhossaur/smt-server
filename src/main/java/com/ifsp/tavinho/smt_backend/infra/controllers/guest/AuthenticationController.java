package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.LoginUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.CreateUserUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.LOGIN;

@RestController
@AllArgsConstructor
@RequestMapping(BASE_API_ROUTE)
public class AuthenticationController {

    private final LoginUseCase login;
    private final CreateUserUseCase createUser;

    @PostMapping(LOGIN)
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginCredentialsDTO credentials) { 
        return login.execute(credentials); 
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO user) {
        return createUser.execute(user);
    }
    
}
