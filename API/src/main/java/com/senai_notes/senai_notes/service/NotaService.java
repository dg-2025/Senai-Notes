package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.DTOs.Nota.NotaRequest;
import com.senai_notes.senai_notes.DTOs.Nota.NotaResponse;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.NotaRepository;
import com.senai_notes.senai_notes.repository.TagRepository;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaService(NotaRepository notaRepository, TagRepository tagRepository,
                       UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todas as notas
    public List<NotaResponse> listarNotas() {
        List<Nota> notas = notaRepository.findAll();
        return notas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // Listar por e-mail
    public List<NotaResponse> listarNotasPorEmail(String email) {
        List<Nota> notas = notaRepository.findByIdUsuarioEmail(email);
        return notas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // Listar por ID do usuário
    public List<NotaResponse> listarNotasPorUsuarioId(Integer id) {
        List<Nota> notas = notaRepository.findByIdUsuario_id(id);
        return notas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // Buscar nota por ID
    public Nota buscarPorId(int id) {
        return notaRepository.findById(id).orElse(null);
    }

    // ✅ Adicionar nota (com imagem como parâmetro separado)
    public Nota adicionarNota(NotaRequest dto, String nomeImagem) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) return null;

        Nota nota = new Nota();
        nota.setTitulo(dto.getTitulo());
        nota.setDescricao(dto.getDescricao());
        nota.setImagem(nomeImagem);
        nota.setIdUsuario(usuario);
        nota.setDataCriacao(OffsetDateTime.now());
        nota.setUltimaEdicao(OffsetDateTime.now());

        List<Tag> tags = new ArrayList<>();
        for (String nomeTag : dto.getTags()) {
            Optional<Tag> tagExistente = tagRepository.findByNomeAndUsuarioId(nomeTag, usuario.getId());
            Tag tag = tagExistente.filter(t -> t.getNota() == null).orElseGet(() -> {
                Tag nova = new Tag();
                nova.setNome(nomeTag);
                nova.setUsuario(usuario);
                nova.setDataCriacao(OffsetDateTime.now());
                return nova;
            });

            tag.setNota(nota);
            tags.add(tag);
        }

        nota.setTags(tags);
        return notaRepository.save(nota);
    }

    // ✅ Adicionar nota (sem imagem)
    public Nota adicionarNota(NotaRequest dto) {
        return adicionarNota(dto, null);
    }

    // ✅ Atualizar nota existente (agora aceita imagem também)
    public Nota atualizarNota(NotaRequest dto, int id, String nomeImagem) {
        Nota notaExistente = buscarPorId(id);
        if (notaExistente == null) return null;

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) return null;

        notaExistente.setTitulo(dto.getTitulo());
        notaExistente.setDescricao(dto.getDescricao());
        notaExistente.setIdUsuario(usuario);
        notaExistente.setUltimaEdicao(OffsetDateTime.now());

        // Só atualiza imagem se foi enviada uma nova
        if (nomeImagem != null) {
            notaExistente.setImagem(nomeImagem);
        }

        // Atualiza tags
        notaExistente.getTags().clear();
        for (String nomeTag : dto.getTags()) {
            Optional<Tag> tagExistente = tagRepository.findByNomeAndUsuarioId(nomeTag, usuario.getId());
            Tag tag = tagExistente.orElseGet(() -> {
                Tag nova = new Tag();
                nova.setNome(nomeTag);
                nova.setUsuario(usuario);
                nova.setDataCriacao(OffsetDateTime.now());
                return nova;
            });
            tag.setNota(notaExistente);
            notaExistente.getTags().add(tag);
        }

        return notaRepository.save(notaExistente);
    }

    // Remover nota
    public Nota removerNota(int id) {
        Nota nota = buscarPorId(id);
        if (nota == null) return null;
        notaRepository.delete(nota);
        return nota;
    }

    // Conversão para DTO de resposta
    private NotaResponse converterParaDTO(Nota nota) {
        NotaResponse dto = new NotaResponse();
        dto.setIdNota(nota.getId());
        dto.setEmail(nota.getIdUsuario().getEmail());
        dto.setTitulo(nota.getTitulo());
        dto.setDescricao(nota.getDescricao());
        dto.setImagem(nota.getImagem());

        List<String> nomesTags = new ArrayList<>();
        for (Tag tag : nota.getTags()) {
            nomesTags.add(tag.getNome());
        }

        dto.setTags(nomesTags);
        return dto;
    }
}
