package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.CadastroTagDTO;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tabela Tag", description = "Relaciona as tags criadas pelos usuarios")

public class TagController {
    //Injeção de dependencias
    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    //Listar todos
    @GetMapping
    public ResponseEntity<List<Tag>> listarTag() {
        List<Tag> tag = tagService.listarTag();
        return ResponseEntity.ok(tag);
    }
    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTag(@PathVariable int id) {
        Tag tagExistente = tagService.bucarPorId(id);
        if (tagExistente == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok().body(tagExistente);
    }
    //Adicionar Tag
    @PostMapping
    public ResponseEntity<?> cadastrarTag(@RequestBody CadastroTagDTO newTag) {
        tagService.adicionarTag(newTag);
        return ResponseEntity.ok().body("Tag adicionada com sucesso");
    }
    //Editar & Atualizar Tag
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTag(@PathVariable int id, @RequestBody Tag newTag) {
        Tag tagExistente = tagService.atualizarTag(newTag, id);
        if (tagExistente == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok().body("Tag atualizada com sucesso");
    }
    //Deletar Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerTag(@PathVariable int id) {
        Tag tagExistente = tagService.removerTag(id);
        if (tagExistente == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        tagService.removerTag(id);
        return ResponseEntity.ok().body(tagExistente);
    }
}
