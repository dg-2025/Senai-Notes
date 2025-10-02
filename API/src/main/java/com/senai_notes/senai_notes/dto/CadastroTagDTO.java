package com.senai_notes.senai_notes.dto;


import com.senai_notes.senai_notes.models.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CadastroTagDTO {
    private Integer usuarioId;
    private String nome;
    // TODO: trocar para DTO de Usuario
}
