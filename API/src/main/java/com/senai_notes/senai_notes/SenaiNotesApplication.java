package com.senai_notes.senai_notes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API de Senai Notes",
        version = "1.0",
        description = "API responsável por gerenciar os recursos de um sistema de notas."
))

public class SenaiNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenaiNotesApplication.class, args);
	}

}
