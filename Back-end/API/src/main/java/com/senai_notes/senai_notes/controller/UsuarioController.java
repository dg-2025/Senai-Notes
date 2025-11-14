package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.DTOs.Email.ResetarSenhaDTO;
import com.senai_notes.senai_notes.DTOs.Usuario.TrocarSenhaDTO;
import com.senai_notes.senai_notes.DTOs.Usuario.UsuarioRequest;
import com.senai_notes.senai_notes.DTOs.Usuario.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    //Recuperar senha
    @PostMapping("/forgot-password")
    @Operation(summary = "Recuperar senha",
            description = "Envia um email com link para o usuario recuperar a senha.")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ResetarSenhaDTO resetarSenhaDTO) {
        usuarioService.recuperarSenha(resetarSenhaDTO.getEmail());
        return ResponseEntity.ok("Se um usuário com este e-mail existir, uma nova senha será enviada.");
    }

    // Adicione dentro de UsuarioController
    @PutMapping("/{id}/alterar-senha")
    @Operation(summary = "Alterar senha", description = "Valida a senha antiga e atualiza para a nova.")
    public ResponseEntity<?> alterarSenha(@PathVariable Integer id, @RequestBody TrocarSenhaDTO dto) {
        try {
            usuarioService.trocarSenha(id, dto.getSenhaAntiga(), dto.getNovaSenha());
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário. Campos obrigatórios: nome, email, senha.")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest novoUsuario) {
        try {
            // Tenta cadastrar
            Usuario usuario = usuarioService.cadastrarUsuario(novoUsuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
