package com.senaiNotes.senainotes.DTOs.Usuario;

import lombok.Data;

@Data
public class UsuarioRequest {
    public String nome;
    public String email;
    public String senha;
}
