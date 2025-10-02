package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.notaDTO.NotaResponse;
import com.senai_notes.senai_notes.dto.notaDTO.NotaRequest;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    // Injeção de dependências
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    // Listar todas as notas
    @GetMapping
    public ResponseEntity<List<Nota>> listarNotas() {
        List<Nota> notas = notaService.listarNotas();
        return ResponseEntity.ok(notas);
    }

    // Listar notas pelo e-mail do usuário
    @GetMapping("/nota-por-email/{email}")
    @Operation(summary = "Mostrar notas do usuário logado pelo e-mail")
    public ResponseEntity<List<NotaResponse>> listarNotasPorEmail(@PathVariable String email) {
        List<NotaResponse> usuarioNota = notaService.listarNotasPorEmail(email);
        return ResponseEntity.ok(usuarioNota);
    }

    // Buscar notas por ID do usuário
    @GetMapping("/buscarporid/{id}")
    @Operation(summary = "Buscar notas por ID do usuário")
    public ResponseEntity<List<NotaResponse>> buscarNotasPorIdUsuario(@PathVariable Integer id) {
        List<NotaResponse> notas = notaService.listarNotasPorUsuarioId(id);
        if (notas == null || notas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notas);
    }

    // Adicionar uma nova nota
    @PostMapping
    @Operation(summary = "Cadastrar uma nova nota")
    public ResponseEntity<?> cadastrarNota(@RequestBody NotaRequest novaNota) {
        Nota notaCriada = notaService.adicionarNota(novaNota);
        if (notaCriada == null) {
            return ResponseEntity.badRequest().body("Erro ao adicionar nota. Verifique os dados enviados.");
        }
        return ResponseEntity.ok("Nota adicionada com sucesso");
    }

    // Editar / Atualizar uma nota
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma nota existente")
    public ResponseEntity<?> atualizarNota(@PathVariable int id, @RequestBody NotaRequest novaNota) {
        Nota notaAtualizada = notaService.atualizarNota(novaNota, id);
        if (notaAtualizada == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok("Nota atualizada com sucesso");
    }

    // Deletar uma nota
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma nota pelo ID")
    public ResponseEntity<?> removerNota(@PathVariable int id) {
        Nota notaRemovida = notaService.removerNota(id);
        if (notaRemovida == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok("Nota removida com sucesso");
    }
}
