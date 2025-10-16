package com.senaiNotes.senainotes.Service;

import com.senaiNotes.senainotes.DTOs.Usuario.UsuarioRequest;
import com.senaiNotes.senainotes.DTOs.Usuario.UsuarioResponse;
import com.senaiNotes.senainotes.Models.Usuario;
import com.senaiNotes.senainotes.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todos os usuários
    public List<UsuarioResponse> ListarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::ConverterParaResponse)
                .collect(Collectors.toList());
    }

    // Buscar usuário por email
    public UsuarioResponse BuscarUsuariosPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) return null;
        return ConverterParaResponse(usuario);
    }

    // Buscar usuário por ID (para exibir dados)
    public UsuarioResponse BuscarUsuarioPorId(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.map(this::ConverterParaResponse).orElse(null);
    }


    // Cadastrar usuário
    public Usuario CadastrarUsuario(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    // Editar usuário
    public UsuarioRequest EditarUsuario(UsuarioRequest dto, Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt == null) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuarioRepository.save(usuario);

        return ConverterParaRequest(usuario);
    }

    // Excluir usuário
    public Usuario ExcluirUsuario(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt == null) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        usuarioRepository.delete(usuario);
        return usuario;
    }

    // Conversor para Response
    public UsuarioResponse ConverterParaResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        return response;
    }

    // Conversor para Request
    public UsuarioRequest ConverterParaRequest(Usuario usuario) {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome(usuario.getNome());
        request.setEmail(usuario.getEmail());
        request.setSenha(usuario.getSenha());
        return request;
    }
}
