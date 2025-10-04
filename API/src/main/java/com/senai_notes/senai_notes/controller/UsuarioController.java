package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.UsuarioDTO.LoginResponse;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioController(UsuarioService usuarioService, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }




    //Metodo de login
    @PostMapping("/api/auth")
    @Operation(summary = "metodo de login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequest loginRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());
        UsuarioResponse usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());
        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        Authentication auth = authenticationManager.authenticate(authToken);
        Instant now = Instant.now();
        long validade = 3600L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senainotes-api") // Quem emitiu o token.
                .issuedAt(now) // Quando foi emitido.
                .expiresAt(now.plusSeconds(validade)) // Quando expira.
                .subject(auth.getName()) // A quem o token pertence (o email do usuário).
                .build();
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(token, usuario));


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

    //Buscar por email
    @GetMapping("/buscar/{email}")
    @Operation(summary = "metodo pra buscar por email")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.buscarPorEmail(email);
        if (usuario == null) {
            ResponseEntity.badRequest().body("usuario nao encontrado");
        }
        return ResponseEntity.ok(usuario);
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