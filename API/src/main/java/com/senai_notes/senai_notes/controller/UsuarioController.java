package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import com.senai_notes.senai_notes.service.UsuarioService;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
// Oi

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final GenericResponseService responseBuilder;

    public UsuarioController(UsuarioService service, UsuarioRepository usuarioRepository, GenericResponseService responseBuilder) {
        usuarioService = service;
        this.usuarioRepository = usuarioRepository;
        this.responseBuilder = responseBuilder;
    }
@GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();

        return ResponseEntity.ok(usuarios);
    }
@PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario)
{

    usuarioService.cadastrarUsuario(usuario);

    return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {

        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario" + id + "Não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {

        Usuario usuario = usuarioService.deletarUsuario(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario" + id + "Não encontrado");
        }

        usuarioRepository.delete(usuario);
        return ResponseEntity.ok(usuario);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(
            @PathVariable Integer id, @RequestBody Usuario usuarioNovo) {
        //1.Tento atualizar o cliente
        Usuario usu = usuarioService.atualizarUsuario(id, usuarioNovo);

        //2.
        if (usu == null) {
            return ResponseEntity.status(404)
                    .body("Cliente nao encontrado");
        }
        //3.Se achar retorno ok
        return ResponseEntity.ok(usu);
    }
}
