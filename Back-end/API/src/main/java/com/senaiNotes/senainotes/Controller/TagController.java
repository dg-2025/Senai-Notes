package com.senaiNotes.senainotes.Controller;

import com.senaiNotes.senainotes.DTOs.Tag.TagRequest;
import com.senaiNotes.senainotes.DTOs.Tag.TagResponse;
import com.senaiNotes.senainotes.Models.Tag;
import com.senaiNotes.senainotes.Service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Listar todas as tags
    @GetMapping
    public ResponseEntity<List<TagResponse>> listarTodasTags() {
        List<TagResponse> tags = tagService.listarTags();
        return ResponseEntity.ok(tags);
    }

    // Buscar tag por ID
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> listarTagPorId(@PathVariable Integer id) {
        TagResponse tag = tagService.buscarTagPorId(id);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok(tag);
    }

    // Buscar tag por nome
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<?> listarTagPorNome(@PathVariable String nome) {
        TagResponse tag = tagService.buscarTagPorNome(nome);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok(tag);
    }

    // Buscar tags por usuário
    @GetMapping("/buscar/usuario/{id}")
    public ResponseEntity<?> listarTagsPorUsuario(@PathVariable Integer id) {
        List<TagResponse> tags = tagService.buscarTagsPorUsuarioId(id);
        if (tags == null) {
            return ResponseEntity.badRequest().body("Nenhuma tag encontrada para este usuário.");
        }
        return ResponseEntity.ok(tags);
    }

    // Cadastrar nova tag
    @PostMapping
    public ResponseEntity<?> cadastrarTag(@RequestBody TagRequest newTag) {
        Tag tag = tagService.adicionarTag(newTag);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não adicionada (usuário pode não existir).");
        }
        return ResponseEntity.ok("Tag adicionada com sucesso");
    }

    // Editar tag existente
    @PutMapping("/editar/id/{id}")
    public ResponseEntity<?> editarTag(@PathVariable Integer id, @RequestBody TagRequest editTag) {
        TagRequest tag = tagService.editarTagPorId(id, editTag);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não foi atualizada");
        }
        return ResponseEntity.ok("Tag atualizada com sucesso");
    }

    // Deletar tag
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerTag(@PathVariable Integer id) {
        Tag tag = tagService.deletarTagPorId(id);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag não encontrada");
        }
        return ResponseEntity.ok("Tag removida com sucesso");
    }
}
