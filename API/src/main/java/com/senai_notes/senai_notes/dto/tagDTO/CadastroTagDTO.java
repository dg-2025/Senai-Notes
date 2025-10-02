package com.senai_notes.senai_notes.dto.tagDTO;


import lombok.Data;

@Data
public class CadastroTagDTO {
    private Integer usuarioId;
    private String nome;
    private String descricao;
}
