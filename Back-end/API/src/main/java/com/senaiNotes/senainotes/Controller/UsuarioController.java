package com.senaiNotes.senainotes.Controller;

import com.senaiNotes.senainotes.DTOs.Usuario.UsuarioRequest;
import com.senaiNotes.senainotes.DTOs.Usuario.UsuarioResponse;
import com.senaiNotes.senainotes.Models.Usuario;
import com.senaiNotes.senainotes.Service.UsuarioService;
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
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.ListarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por e-mail
    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.BuscarUsuariosPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Buscar usuário por ID
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        UsuarioResponse usuario = usuarioService.BuscarUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Cadastrar novo usuário
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest novoUsuario) {
        Usuario usuario = usuarioService.CadastrarUsuario(novoUsuario);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário");
        }
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    // Atualizar usuário existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody UsuarioRequest dadosAtualizados, @PathVariable Integer id) {
        UsuarioRequest atualizado = usuarioService.EditarUsuario(dadosAtualizados, id);
        if (atualizado == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    // Remover usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Integer id) {
        Usuario removido = usuarioService.ExcluirUsuario(id);
        if (removido == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário removido com sucesso");
    }
}
