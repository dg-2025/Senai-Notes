package com.senai_notes.senai_notes.DTOs.Nota;

import lombok.Data;

import java.util.List;

@Data
public class NotaResponse {
    private Integer idNota;
    private String email;
    private String titulo;
    private String descricao;
    private String imagem;
    private List<String> tags;
}

