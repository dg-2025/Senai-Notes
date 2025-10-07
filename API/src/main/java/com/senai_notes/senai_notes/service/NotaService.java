package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.notaDTO.NotaRequest;
import com.senai_notes.senai_notes.dto.notaDTO.NotaResponse;
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

    // injeção de dependências
    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaService(NotaRepository notaRepository, TagRepository tagRepository,
                       UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // listar todas as notas
    public List<Nota> listarNotas() {
        return notaRepository.findAll();
    }

    // listar notas por e-mail do usuário
    public List<NotaResponse> listarNotasPorEmail(String email) {
        List<Nota> notas = notaRepository.findByIdUsuarioEmail(email);
        return notas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // listar notas por ID do usuário
    public List<NotaResponse> listarNotasPorUsuarioId(Integer id) {
        List<Nota> notas = notaRepository.findByIdUsuario_id(id);
        return notas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // buscar nota por ID (única)
    public Nota buscarPorId(int id) {
        return notaRepository.findById(id).orElse(null);
    }

    // adicionar nova nota
    public Nota adicionarNota(NotaRequest dto) {

        // buscar usuário
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) {
            return null;
        }

        // criar nova nota
        Nota nota = new Nota();
        nota.setTitulo(dto.getTitulo());
        nota.setDescricao(dto.getDescricao());
        nota.setImagem(dto.getImagem());
        nota.setIdUsuario(usuario);
        nota.setDataCriacao(OffsetDateTime.now());
        nota.setUltimaEdicao(OffsetDateTime.now());

        // criar ou reaproveitar tags
        List<Tag> tags = new ArrayList<>();
        for (String nomeTag : dto.getTags()) {
            Optional<Tag> tagExistente = tagRepository.findByNomeAndUsuarioId(nomeTag, usuario.getId());
            Tag tag;
            if (tagExistente.isPresent() && tagExistente.get().getNota() == null) {
                tag = tagExistente.get();
            } else {
                tag = new Tag();
                tag.setNome(nomeTag);
                tag.setUsuario(usuario);
                tag.setDataCriacao(OffsetDateTime.now());
            }
            tag.setNota(nota);
            tags.add(tag);
        }
        nota.setTags(tags);

        return notaRepository.save(nota);
    }

    // atualizar nota existente
    public Nota atualizarNota(NotaRequest dto, int id) {
        Nota notaExistente = buscarPorId(id);
        if (notaExistente == null) {
            return null;
        }

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) {
            return null;
        }

        // Atualiza campos simples
        notaExistente.setTitulo(dto.getTitulo());
        notaExistente.setDescricao(dto.getDescricao());
        notaExistente.setImagem(dto.getImagem());
        notaExistente.setIdUsuario(usuario);
        notaExistente.setUltimaEdicao(OffsetDateTime.now());

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

    // remover nota
    public Nota removerNota(int id) {
        Nota nota = buscarPorId(id);
        if (nota == null) {
            return null;
        }

        notaRepository.delete(nota);
        return nota;
    }

    // converter Nota -> NotaResponse DTO
    private NotaResponse converterParaDTO(Nota nota) {
        NotaResponse dto = new NotaResponse();
        dto.setIdNota(nota.getId());
        dto.setEmail(nota.getIdUsuario().getEmail());
        dto.setTitulo(nota.getTitulo());
        dto.setDescricao(nota.getDescricao());
        dto.setImagem(nota.getImagem());

        // extrair nomes das tags
        List<String> nomesTags = new ArrayList<>();
        for (Tag tag : nota.getTags()) {
            nomesTags.add(tag.getNome());
        }

        dto.setTags(nomesTags);
        return dto;
    }
}
