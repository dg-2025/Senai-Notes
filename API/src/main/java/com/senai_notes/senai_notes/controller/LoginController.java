package com.senai_notes.senai_notes.controller;

import com.senai_notes.senai_notes.dto.UsuarioDTO.LoginResponse;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioResponse;
import com.senai_notes.senai_notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/login")
@SecurityRequirement(name = "bearerAuth")

public class LoginController {

    private final UsuarioService usuarioService;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginController(UsuarioService usuarioService, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    //Metodo de login
    @PostMapping()
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
}
