package com.senai_notes.senai_notes.dto;

import lombok.Data;

@Data
public class NotaResponse {
    private String Usuario;
    private String tag;
    private String email;
    private String titulo;
    private String descricao;
    private String imagem;
}
