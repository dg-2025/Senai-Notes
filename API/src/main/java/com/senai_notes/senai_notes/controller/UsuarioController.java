package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import com.senai_notes.senai_notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        UsuarioResponse dto = usuarioService.converterParaResponse(usuario);
        return ResponseEntity.ok(dto);
    }

    // Cadastrar novo usuário
    @PostMapping
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest novoUsuario) {
        Usuario usuario = usuarioService.cadastrarUsuario(novoUsuario);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário");
        }
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    // Atualizar usuário existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioRequest dadosAtualizados) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, dadosAtualizados);
        if (atualizado == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    // Remover usuário
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um usuário por ID")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {
        Usuario removido = usuarioService.deletarUsuario(id);
        if (removido == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário removido com sucesso");
    }
}