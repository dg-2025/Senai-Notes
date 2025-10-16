package com.senaiNotes.senainotes.Service;

import com.senaiNotes.senainotes.DTOs.Tag.TagRequest;
import com.senaiNotes.senainotes.DTOs.Tag.TagResponse;
import com.senaiNotes.senainotes.Models.Tag;
import com.senaiNotes.senainotes.Models.Usuario;
import com.senaiNotes.senainotes.Repository.TagRepository;
import com.senaiNotes.senainotes.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todas as tags
    public List<TagResponse> listarTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::ConverterParaResponse)
                .collect(Collectors.toList());
    }

    // Buscar tag por ID
    public TagResponse buscarTagPorId(Integer id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.map(this::ConverterParaResponse).orElse(null);
    }

    // Buscar tag por nome
    public TagResponse buscarTagPorNome(String nome) {
        Tag tag = tagRepository.findByNome(nome);
        return (tag != null) ? ConverterParaResponse(tag) : null;
    }

    // ✅ Buscar TODAS as tags de um usuário
    public List<TagResponse> buscarTagsPorUsuarioId(Integer usuarioId) {
        List<Tag> tags = tagRepository.findByUsuarioId(usuarioId);
        return tags.stream()
                .map(this::ConverterParaResponse)
                .collect(Collectors.toList());
    }

    // Adicionar nova tag
    public Tag adicionarTag(TagRequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if (usuario == null) return null;

        Tag tag = new Tag();
        tag.setUsuario(usuario); // ✅ Nome do campo atualizado
        tag.setNome(dto.getNome());
        return tagRepository.save(tag);
    }

    // Editar tag existente
    public TagRequest editarTagPorId(Integer id, TagRequest dto) {
        Optional<Tag> tagExistente = tagRepository.findById(id);
        if (tagExistente.isEmpty()) {
            return null;
        }

        Tag tag = tagExistente.get();
        tag.setNome(dto.getNome());
        tagRepository.save(tag);
        return ConverterParaRequest(tag);
    }

    // Deletar tag
    public Tag deletarTagPorId(Integer id) {
        Optional<Tag> tagExistente = tagRepository.findById(id);
        if (tagExistente.isEmpty()) {
            return null;
        }

        tagRepository.delete(tagExistente.get());
        return tagExistente.get();
    }

    // Conversor para Request
    public TagRequest ConverterParaRequest(Tag tag) {
        TagRequest request = new TagRequest();
        request.setNome(tag.getNome());
        request.setIdUsuario(tag.getUsuario().getId()); // Adicionado para preencher o DTO corretamente
        return request;
    }

    // Conversor para Response
    public TagResponse ConverterParaResponse(Tag tag) {
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setNome(tag.getNome());
        return response;
    }
}
