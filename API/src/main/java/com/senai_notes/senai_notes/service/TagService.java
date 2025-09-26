package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    //injeção de dependencias
    private final TagRepository tagRepository;
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
    public Tag adiconarTag(Tag newTag){
        return tagRepository.save(newTag);
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