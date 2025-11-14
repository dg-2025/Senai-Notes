package com.senai_notes.senai_notes.DTOs.Tag;


import lombok.Data;

@Data
public class TagRequest {
    private Integer usuarioId;
    private String nome;
}
