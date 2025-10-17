package com.senai_notes.senai_notes.DTOs.Nota;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NotaRequest {
    private Integer idUsuario;
    private String titulo;
    private String descricao;
    private List<String> tags;
    private MultipartFile imagem;
}

