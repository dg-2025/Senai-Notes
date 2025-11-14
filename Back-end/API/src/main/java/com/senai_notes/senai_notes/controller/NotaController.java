package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.DTOs.Nota.NotaRequest;
import com.senai_notes.senai_notes.DTOs.Nota.NotaResponse;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.service.ArmazenamentoService;
import com.senai_notes.senai_notes.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@SecurityRequirement(name = "bearerAuth")
public class NotaController {

    // Injeção de dependências
    private final NotaService notaService;
    private final ArmazenamentoService armazenamentoService;

    public NotaController(NotaService notaService, ArmazenamentoService armazenamentoService) {
        this.notaService = notaService;
        this.armazenamentoService = armazenamentoService;
    }

    // Listar todas as notas
    @GetMapping
    @Operation(summary = "Listar todas as notas", description = "Retorna todas as notas existentes no sistema.")
    public ResponseEntity<List<NotaResponse>> listarNotas() {
        List<NotaResponse> notas = notaService.listarNotas();
        return ResponseEntity.ok(notas);
    }

    // Listar notas pelo e-mail do usuário
    @GetMapping("/nota-por-email/{email}")
    @Operation(summary = "Listar notas por e-mail do usuário", description = "Mostra todas as notas vinculadas ao e-mail informado.")
    public ResponseEntity<List<NotaResponse>> listarNotasPorEmail(@PathVariable String email) {
        List<NotaResponse> usuarioNota = notaService.listarNotasPorEmail(email);
        return ResponseEntity.ok(usuarioNota);
    }

    // Buscar notas por ID do usuário
    @GetMapping("/buscarporid/{id}")
    @Operation(summary = "Listar notas por ID do usuário", description = "Retorna todas as notas associadas ao ID do usuário informado.")
    public ResponseEntity<List<NotaResponse>> buscarNotasPorIdUsuario(@PathVariable Integer id) {
        List<NotaResponse> notas = notaService.listarNotasPorUsuarioId(id);
        if (notas == null || notas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notas);
    }

    // Adicionar uma nova nota com imagem
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cadastrar nova nota com imagem", description = "Cria uma nova nota com uma imagem enviada.")
    public ResponseEntity<?> cadastrarNota(@ModelAttribute NotaRequest notaRequest) {

        MultipartFile imagem = notaRequest.getImagem();
        String nomeArquivo = null;

        if (imagem != null && !imagem.isEmpty()) {
            nomeArquivo = armazenamentoService.salvarArquivo(imagem);
        }

        Nota notaCriada = notaService.adicionarNota(notaRequest, nomeArquivo);
        if (notaCriada == null) {
            return ResponseEntity.badRequest().body("Erro ao adicionar nota");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Nota criada com sucesso");
    }


    // Servir imagem
    @GetMapping("/imagens/{nomeDoArquivo}")
    @Operation(summary = "Servir imagem de nota", description = "Retorna a imagem salva pelo nome do arquivo.")
    public ResponseEntity<Resource> servirImagem(@PathVariable String nomeDoArquivo) {
        Resource arquivo = armazenamentoService.carregarArquivo(nomeDoArquivo);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(arquivo);
    }

    // Editar / Atualizar uma nota
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualizar nota existente", description = "Atualiza uma nota existente pelo ID.")
    public ResponseEntity<?> atualizarNota(
            @PathVariable int id,
            @ModelAttribute NotaRequest novaNota) {

        MultipartFile imagem = novaNota.getImagem();
        String nomeImagem = null;

        if (imagem != null && !imagem.isEmpty()) {
            nomeImagem = armazenamentoService.salvarArquivo(imagem);
        }

        Nota notaAtualizada = notaService.atualizarNota(novaNota, id, nomeImagem);
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
