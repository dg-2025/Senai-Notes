package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.DTOs.Usuario.UsuarioRequest;
import com.senai_notes.senai_notes.DTOs.Usuario.UsuarioResponse;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //recuperar senha
    public void recuperarSenha(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            String novaSenha = RandomStringUtils.randomAlphanumeric(10);
            String senhaCodificada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCodificada);
            usuarioRepository.save(usuario);
            emailService.enviarEmail(usuario.getEmail(), novaSenha);
        });
        }
    // trocar senha
    // Adicione dentro de UsuarioService
    public void trocarSenha(Integer id, String senhaAntiga, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 1. Verifica se a senha antiga bate com a Hash do banco
        if (!passwordEncoder.matches(senhaAntiga, usuario.getSenha())) {
            throw new RuntimeException("A senha antiga está incorreta!");
        }

        // 2. Criptografa a nova senha antes de salvar
        String novaSenhaCodificada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(novaSenhaCodificada);

        usuarioRepository.save(usuario);
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
    public UsuarioResponse buscarPorEmail(String email) {
        Usuario usuarioExistente = usuarioRepository.findByEmail(email).orElse(null);
        if (usuarioExistente == null) return null;

        return converterParaResponse(usuarioExistente);

    }


    // Cadastrar novo usuário
    public Usuario cadastrarUsuario(UsuarioRequest dto) {
        Usuario existente = usuarioRepository.findByEmail(dto.getEmail()).orElse(null);
        if (existente != null) {
            throw new RuntimeException("Este e-mail já está cadastrado!");
        }
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