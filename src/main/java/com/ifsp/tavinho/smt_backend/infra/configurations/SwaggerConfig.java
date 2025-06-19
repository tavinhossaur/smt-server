package com.ifsp.tavinho.smt_backend.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(this.applicationProperties.getProjectName() + " API")
                .version(this.applicationProperties.getProjectVersion())
                .description(this.applicationProperties.getProjectDescription())
                .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
                .contact(new Contact()
                    .name("Octávio Barassa")
                    .email("octavio.barassa@aluno.ifsp.edu.br")
                )
            );
    }
}
