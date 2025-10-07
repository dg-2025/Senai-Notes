package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    // injeção de dependencias
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    @Operation(summary = "Listar todos os usuários",
            description = "Retorna todos os usuários cadastrados no sistema.")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu ID.")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        UsuarioResponse dto = usuarioService.converterParaResponse(usuario);
        return ResponseEntity.ok(dto);
    }

    // Buscar por email
    @GetMapping("/buscar/{email}")
    @Operation(summary = "Buscar usuário por e-mail",
            description = "Retorna os dados de um usuário pelo e-mail informado.")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.buscarPorEmail(email);
        if (usuario == null) {
            ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Cadastrar novo usuário
    @PostMapping("/cadastras")
    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário. Campos obrigatórios: nome, email, senha.")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest novoUsuario) {
        Usuario usuario = usuarioService.cadastrarUsuario(novoUsuario);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário");
        }
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    // Atualizar usuário existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário existente",
            description = "Atualiza os dados de um usuário pelo seu ID.")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioRequest dadosAtualizados) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, dadosAtualizados);
        if (atualizado == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    // Remover usuário
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário",
            description = "Exclui um usuário existente pelo ID informado.")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {
        Usuario removido = usuarioService.deletarUsuario(id);
        if (removido == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário removido com sucesso");
    }
}
