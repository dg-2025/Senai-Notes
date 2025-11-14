package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.DTOs.Tag.TagRequest;
import com.senai_notes.senai_notes.DTOs.Tag.TagResponse;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@SecurityRequirement(name = "bearerAuth")
public class TagController {

    // Injeção de dependências
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Listar todas as tags
    @GetMapping
    @Operation(summary = "Listar todas as tags",
            description = "Retorna todas as tags cadastradas vinculadas ao e-mail do usuário.")
    public ResponseEntity<List<TagResponse>> listarTags(String email) {
        List<TagResponse> tags = tagService.listarTags(email);
        return ResponseEntity.ok(tags);
    }

    // Buscar tag por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tag por ID",
            description = "Retorna uma tag específica pelo seu ID.")
    public ResponseEntity<?> buscarTag(@PathVariable int id) {
        List<TagResponse> tag = tagService.buscarPorId(id);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok(tag);
    }

    // Adicionar nova tag
    @PostMapping
    @Operation(summary = "Cadastrar nova tag",
            description = "Cria uma nova tag. Campos esperados: nome, idUsuario.")
    public ResponseEntity<?> cadastrarTag(@RequestBody TagRequest newTag) {
        Tag tagCriada = tagService.adicionarTag(newTag);
        if (tagCriada == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Tag adicionada com sucesso");
    }

    // Atualizar tag
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tag existente",
            description = "Atualiza os dados de uma tag existente pelo ID.")
    public ResponseEntity<?> atualizarTag(@PathVariable int id, @RequestBody TagResponse dto) {
        Tag tagAtualizada = tagService.atualizarTag(dto, id);
        if (tagAtualizada == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok("Tag atualizada com sucesso");
    }

    // Remover tag
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover tag",
            description = "Exclui uma tag existente pelo ID informado.")
    public ResponseEntity<?> removerTag(@PathVariable int id) {
        Tag tagRemovida = tagService.removerTag(id);
        if (tagRemovida == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok("Tag removida com sucesso");
    }
}
