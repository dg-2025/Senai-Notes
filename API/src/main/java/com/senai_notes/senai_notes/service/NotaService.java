package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.notaDTO.NotaResponse;
import com.senai_notes.senai_notes.dto.notaDTO.NotaRequest;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.NotaRepository;
import com.senai_notes.senai_notes.repository.TagRepository;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaService {

    // injeção de dependências
    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaService(NotaRepository notaRepository, TagRepository tagRepository, UsuarioRepository usuarioRepository) {
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
        Tag tag = tagRepository.findById(dto.getIdTag()).orElse(null);
        if (tag == null) {
            return null;
        }

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) {
            return null;
        }

        Nota nota = new Nota();
        nota.setTitulo(dto.getTitulo());
        nota.setDescricao(dto.getDescricao());
        nota.setImagem(dto.getImagem());
        nota.setIdTag(tag);
        nota.setIdUsuario(usuario);

        return notaRepository.save(nota);
    }

    // atualizar nota existente
    public Nota atualizarNota(NotaRequest dto, int id) {
        Nota notaExistente = buscarPorId(id);
        if (notaExistente == null) {
            return null;
        }

        Tag tag = tagRepository.findById(dto.getIdTag()).orElse(null);
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);

        if (tag == null || usuario == null) {
            return null;
        }

        notaExistente.setTitulo(dto.getTitulo());
        notaExistente.setDescricao(dto.getDescricao());
        notaExistente.setImagem(dto.getImagem());
        notaExistente.setIdTag(tag);
        notaExistente.setIdUsuario(usuario);

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

    // converter Nota → NotaResponse DTO
    private NotaResponse converterParaDTO(Nota nota) {
        NotaResponse dto = new NotaResponse();
        dto.setEmail(nota.getIdUsuario().getEmail());
        dto.setTitulo(nota.getTitulo());
        dto.setDescricao(nota.getDescricao());
        dto.setImagem(nota.getImagem());
        return dto;
    }
}