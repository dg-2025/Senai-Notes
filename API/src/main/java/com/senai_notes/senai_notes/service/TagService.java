package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.tagDTO.CadastroTagDTO;
import com.senai_notes.senai_notes.dto.tagDTO.ListarTagDTO;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.TagRepository;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    // Injeção de dependências
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todas as tags
    public List<ListarTagDTO> listarTags(String email) {
        List<Tag> tags = tagRepository.findByUsuarioEmail(email);
        return tags.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    // Buscar tag por ID
    public List<ListarTagDTO> buscarPorId(int id) {
        return tagRepository.findById(id).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    // Adicionar nova tag
    public Tag adicionarTag(CadastroTagDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        if (usuario == null) return null;

        Tag tag = new Tag();
        tag.setNome(dto.getNome());
        tag.setDescricao(dto.getDescricao());
        tag.setDataCriacao(OffsetDateTime.now());
        tag.setUsuario(usuario);

        return tagRepository.save(tag);
    }

    // Atualizar tag existente
    public Tag atualizarTag(ListarTagDTO dto, int id) {
        Tag tagExistente = tagRepository.findById(id).orElse(null);
        if (tagExistente == null) return null;

        tagExistente.setNome(dto.getNome());
        tagExistente.setDescricao(dto.getDescricao());

        return tagRepository.save(tagExistente);
    }


    // Remover tag
    public Tag removerTag(int id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        if (tag == null) {
            return null;
        }
        tagRepository.delete(tag);
        return tag;
    }

    // Conversão para DTO de saída
    private ListarTagDTO converterParaDTO(Tag tag) {
        ListarTagDTO dto = new ListarTagDTO();
        dto.setId(tag.getId());
        dto.setNome(tag.getNome());
        dto.setDescricao(tag.getDescricao());
        return dto;
    }

}