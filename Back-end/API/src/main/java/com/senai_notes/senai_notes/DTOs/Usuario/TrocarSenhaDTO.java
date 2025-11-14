package com.senai_notes.senai_notes.DTOs.Usuario;

import lombok.Data;

@Data
public class TrocarSenhaDTO {
    private String senhaAntiga;
    private String novaSenha;
}