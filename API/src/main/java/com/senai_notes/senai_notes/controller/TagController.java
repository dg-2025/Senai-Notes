package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.tagDTO.CadastroTagDTO;
import com.senai_notes.senai_notes.dto.tagDTO.ListarTagDTO;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    // Injeção de dependências
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Listar todas as tags
    @GetMapping
    @Operation(summary = "Listar todas as tags cadastradas")
    public ResponseEntity<List<ListarTagDTO>> listarTags(String email) {
        List<ListarTagDTO> tags = tagService.listarTags(email);
        return ResponseEntity.ok(tags);
    }

    // Buscar tag por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tag por ID")
    public ResponseEntity<?> buscarTag(@PathVariable int id) {
        Tag tag = tagService.buscarPorId(id);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok(tag);
    }

    // Adicionar nova tag
    @PostMapping
    @Operation(summary = "Cadastrar nova tag")
    public ResponseEntity<?> cadastrarTag(@RequestBody CadastroTagDTO newTag) {
        Tag tagCriada = tagService.adicionarTag(newTag);
        if (tagCriada == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Tag adicionada com sucesso");
    }

    // Atualizar tag
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma tag existente")
    public ResponseEntity<?> atualizarTag(@PathVariable int id, @RequestBody Tag novaTag) {
        Tag tagAtualizada = tagService.atualizarTag(novaTag, id);
        if (tagAtualizada == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok("Tag atualizada com sucesso");
    }

    // Remover tag
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma tag pelo ID")
    public ResponseEntity<?> removerTag(@PathVariable int id) {
        Tag tagRemovida = tagService.removerTag(id);
        if (tagRemovida == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok("Tag removida com sucesso");
    }
}
