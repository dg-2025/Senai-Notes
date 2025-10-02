package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.CadastroTagDTO;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.TagRepository;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TagService {

    //injeção de dependencias
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    // private final UsuarioRepository usuarioRepository;
    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
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
        Usuario u = usuarioRepository.getReferenceById(dto.getUsuarioId());


        Tag tag = new Tag();

        tag.setNome(dto.getNome());
        tag.setDataCriacao(OffsetDateTime.now());

        // Passar o usuario que encontrei pra Tag
        tag.setUsuario(u);

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