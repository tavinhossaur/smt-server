package com.ifsp.tavinho.smt_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ifsp.tavinho.smt_backend.infra.repositories")
public class SmtApplication {
	public static void main(String[] args) {
        SpringApplication.run(SmtApplication.class, args);
	}
}
