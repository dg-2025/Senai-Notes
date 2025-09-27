package com.senai_notes.senai_notes.dto;

import lombok.Data;

@Data
public class NotaRequest {
    private Integer idUsuario;
    private Integer idTag;
    private String titulo;
    private String descricao;
    private String imagem;
}
