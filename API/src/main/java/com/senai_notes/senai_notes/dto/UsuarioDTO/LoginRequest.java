package com.senai_notes.senai_notes.dto.UsuarioDTO;

import lombok.Data;

@Data
public class LoginRequest  {
    public String email;
    public String senha;
}