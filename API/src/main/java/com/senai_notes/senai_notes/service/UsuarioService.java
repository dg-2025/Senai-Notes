package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.UsuarioDTO.LoginRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioRequest;
import com.senai_notes.senai_notes.dto.UsuarioDTO.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar todos os usuários
    public List<UsuarioResponse> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Buscar por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }


    // Cadastrar novo usuário
    public Usuario cadastrarUsuario(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        // Criptografar senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }



    // Atualizar usuário existente
    public Usuario atualizarUsuario(Integer id, UsuarioRequest dto) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente == null) return null;

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());

        // Atualizar com a nova senha criptografada
        usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuarioExistente);
    }

    // Deletar usuário
    public Usuario deletarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);
        if (usuario == null) return null;

        usuarioRepository.delete(usuario);
        return usuario;
    }

    // Conversor de Usuario  UsuarioResponse
    public UsuarioResponse converterParaResponse(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}