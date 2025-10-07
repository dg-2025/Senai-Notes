package com.senai_notes.senai_notes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API de Senai Notes",
        version = "1.0",
        description = "API responsável por gerenciar os recursos de um sistema de notas."
))
@SecurityScheme(
        name = "bearerAuth", // 1. Um nome para referenciar este esquema de segurança.
        type = SecuritySchemeType.HTTP, // 2. O tipo de segurança. HTTP é usado para Bearer, Basic Auth, etc.
        scheme = "bearer", // 3. O esquema específico. "bearer" para JWT.
        bearerFormat = "JWT" // 4. Um "hin  t" para o formato do token.
)
public class SenaiNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenaiNotesApplication.class, args);
	}

}
