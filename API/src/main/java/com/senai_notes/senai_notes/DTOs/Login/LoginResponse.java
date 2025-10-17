package com.senai_notes.senai_notes.DTOs.Login;

import com.senai_notes.senai_notes.DTOs.Usuario.UsuarioResponse;

public record LoginResponse (String token, UsuarioResponse usuario){
}
