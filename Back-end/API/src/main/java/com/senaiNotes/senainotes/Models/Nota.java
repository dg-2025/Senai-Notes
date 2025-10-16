package com.senaiNotes.senainotes.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "nota", schema = "notes")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "imagem_url", length = Integer.MAX_VALUE)
    private String imagemUrl;

    @Column(name = "titulo", nullable = false, length = Integer.MAX_VALUE)
    private String titulo;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_criacao")
    private Instant dataCriacao;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_edicao")
    private Instant dataEdicao;

}