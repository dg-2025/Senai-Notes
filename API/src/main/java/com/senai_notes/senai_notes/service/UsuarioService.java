package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository repo) {
        usuarioRepository = repo;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario cadastrarUsuario(Usuario usu) {
        return usuarioRepository.save(usu);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario deletarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            return null;

        }
        usuarioRepository.delete(usuario);
        return usuario;
    }

    public Usuario atualizarUsuario(Integer id, Usuario usuarioNovo) {

        Usuario usuarioAntigo = buscarPorId(id);

        if (usuarioAntigo != null) {
            return null;
        }
        usuarioAntigo.setSenha(usuarioNovo.getSenha());
        usuarioAntigo.setNome(usuarioNovo.getNome());
        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        return usuarioRepository.save(usuarioAntigo);
    }
}