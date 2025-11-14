package com.senai_notes.senai_notes.DTOs.Usuario;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer id;
    private String nome;
    private String email;
}
