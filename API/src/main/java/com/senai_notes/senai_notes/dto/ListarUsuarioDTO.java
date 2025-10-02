package com.senai_notes.senai_notes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ListarUsuarioDTO {

        // Campos que queremos expor na API
        private String nome;
        private String email;
        private OffsetDateTime dataCriacao;
}

