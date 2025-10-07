package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.notaDTO.NotaResponse;
import com.senai_notes.senai_notes.dto.notaDTO.NotaRequest;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@SecurityRequirement(name = "bearerAuth")
public class NotaController {

    // Injeção de dependências
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    // Listar todas as notas
    @GetMapping
    @Operation(summary = "Listar todas as notas",
            description = "Retorna todas as notas existentes no sistema.")
    public ResponseEntity<List<Nota>> listarNotas() {
        List<Nota> notas = notaService.listarNotas();
        return ResponseEntity.ok(notas);
    }

    // Listar notas pelo e-mail do usuário
    @GetMapping("/nota-por-email/{email}")
    @Operation(summary = "Listar notas por e-mail do usuário",
            description = "Mostra todas as notas vinculadas ao e-mail informado.")
    public ResponseEntity<List<NotaResponse>> listarNotasPorEmail(@PathVariable String email) {
        List<NotaResponse> usuarioNota = notaService.listarNotasPorEmail(email);
        return ResponseEntity.ok(usuarioNota);
    }

    // Buscar notas por ID do usuário
    @GetMapping("/buscarporid/{id}")
    @Operation(summary = "Listar notas por ID do usuário",
            description = "Retorna todas as notas associadas ao ID do usuário informado.")
    public ResponseEntity<List<NotaResponse>> buscarNotasPorIdUsuario(@PathVariable Integer id) {
        List<NotaResponse> notas = notaService.listarNotasPorUsuarioId(id);
        if (notas == null || notas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notas);
    }

    // Adicionar uma nova nota
    @PostMapping
    @Operation(summary = "Cadastrar nova nota",
            description = "Cria uma nova nota." +
                    " Campos esperados: idUsuario, titulo, descricao, imagem (opcional), tags (lista), arquivado.")
    public ResponseEntity<?> cadastrarNota(@RequestBody NotaRequest novaNota) {
        Nota notaCriada = notaService.adicionarNota(novaNota);
        if (notaCriada == null) {
            return ResponseEntity.badRequest().body("Erro ao adicionar nota. Verifique os dados enviados.");
        }
        return ResponseEntity.ok("Nota adicionada com sucesso");
    }

    // Editar / Atualizar uma nota
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar nota existente",
            description = "Atualiza uma nota existente pelo ID." +
                    " Campos esperados: idUsuario, titulo, descricao, imagem (opcional), tags (lista), arquivado.")
    public ResponseEntity<?> atualizarNota(@PathVariable int id, @RequestBody NotaRequest novaNota) {
        Nota notaAtualizada = notaService.atualizarNota(novaNota, id);
        if (notaAtualizada == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok("Nota atualizada com sucesso");
    }

    // Deletar uma nota
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover nota", description = "Remove uma nota existente pelo ID informado.")
    public ResponseEntity<?> removerNota(@PathVariable int id) {
        Nota notaRemovida = notaService.removerNota(id);
        if (notaRemovida == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok("Nota removida com sucesso");
    }
}
