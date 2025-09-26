package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.service.NotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")

public class NotaController {

    //injeção de dependencias
    private final NotaService notaService;
    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Nota>> listarNotas() {
        List<Nota> notas = notaService.listarNotas();
        return ResponseEntity.ok(notas);
    }

    //buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNota(@PathVariable int id) {
        Nota notaExistente = notaService.bucarPorId(id);
        if (notaExistente == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok().body(notaExistente);
    }

    //Adicionar Nota
    @PostMapping
    public ResponseEntity<?> cadastrarNota(@RequestBody Nota newNota) {
        notaService.adiconarNota(newNota);
        return ResponseEntity.ok().body("Nota adicionada com sucesso");
    }

    //Editar / Atualizar nota
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNota(@PathVariable int id, @RequestBody Nota newNota) {
        Nota notaExistente = notaService.atualizarNota(newNota, id);
        if (notaExistente == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok().body("Nota atualizada com sucesso");
    }

    //Deletar nota
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerNota(@PathVariable int id) {
        Nota notaExistente = notaService.removerNota(id);
        if (notaExistente == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        notaService.removerNota(id);
        return ResponseEntity.ok().body(notaExistente);
    }

}
