package com.ifsp.tavinho.smt_backend.infra.database;

import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.application.services.admin.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    @Override
    public void run(String... args) throws Exception {
        if (applicationProperties.isDevEnvironment() && applicationProperties.isDatabaseSeederEnabled()) this.seedAdminUser();
    }

    private User seedAdminUser() {
        if (this.userService.findByEmail("admin@admin.com") != null) return null;
        return this.userService.create(new UserDTO("Admin", "admin@admin.com", null, true));
    }
}
