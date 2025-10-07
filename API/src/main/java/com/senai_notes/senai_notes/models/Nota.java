package com.senai_notes.senai_notes.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "nota", schema = "notes")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @Column(name = "titulo", nullable = false, length = Integer.MAX_VALUE)
    private String titulo;

    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    @Column(name = "imagem", length = Integer.MAX_VALUE)
    private String imagem;

    @Column(name = "arquivado")
    private Boolean arquivado;

    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Column(name = "ultima_edicao")
    private OffsetDateTime ultimaEdicao;

}