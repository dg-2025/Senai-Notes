package com.senai_notes.senai_notes.dto.UsuarioDTO;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nome;
    private String email;
    private String senha;
}
