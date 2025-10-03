package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.CadastroUsuarioDTO;
import com.senai_notes.senai_notes.dto.ListarUsuarioDTO;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import com.senai_notes.senai_notes.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) {
        usuarioService = service;

    }


    @PostMapping
    public ResponseEntity<CadastroUsuarioDTO> cadastrar(@RequestBody CadastroUsuarioDTO dto) {
        // 1. O corpo da requisição JSON é convertido para a DTO.
        // 2. O serviço é chamado, e ele retorna a mesma DTO como confirmação.
        CadastroUsuarioDTO usuarioCadastrado = usuarioService.cadastrar(dto);

        // 3. Retorna o status 201 Created e, no corpo, a DTO que foi utilizada para o cadastro.
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }



@GetMapping
   // public ResponseEntity<List<Usuario>> listarUsuarios() {
    //    List<Usuario> usuarios = usuarioService.listarTodos();

     //   return ResponseEntity.ok(usuarios);

public ResponseEntity<List<ListarUsuarioDTO>> listar() {
    // Chama o serviço que já retorna a lista no formato correto (DTO)
    List<ListarUsuarioDTO> usuarios = usuarioService.listarTodos();
    // Retorna 200 OK com a lista no corpo da resposta
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
