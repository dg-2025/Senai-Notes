package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.CadastroTagDTO;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TagService {

    //injeção de dependencias
    private final TagRepository tagRepository;
    // private final UsuarioRepository usuarioRepository;
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    //listar Tag
    public List<Tag> listarTag() {
        return tagRepository.findAll();
    }
    //Buscar por id
    public Tag bucarPorId(int id){
        return tagRepository.findById(id).orElse(null);
    }
    //Adicionar Tag
    public Tag adicionarTag(CadastroTagDTO dto){
        // Encontrar o usuario
        // TODO: Usuario u = usuarioRepository.findById(dto.getUsuarioId());


        Tag tag = new Tag();

        tag.setNome(dto.getNome());
        tag.setDescricao(dto.getDescricao());
        tag.setDataCriacao(OffsetDateTime.now());

        // Passar o usuario que encontrei pra Tag
        // TODO: tag.setUsuario(u);

        return tagRepository.save(tag);
    }
    //Atualizar Tag / editar Tag
    public Tag atualizarTag(Tag updateTag, int id){
        Tag tagExistente = this.bucarPorId(id);
        if (tagExistente == null){
            return null;
        }
        tagExistente.setNome(updateTag.getNome());
        tagExistente.setDescricao(updateTag.getDescricao());
        tagExistente.setDataCriacao(updateTag.getDataCriacao());
        return tagRepository.save(tagExistente);
    }
    //Deletar Tag
    public Tag removerTag(int id){
        Tag tagExistente = this.bucarPorId(id);
        if (tagExistente == null){
            return null;
        }
        tagRepository.delete(tagExistente);
        return tagExistente;
    }
}