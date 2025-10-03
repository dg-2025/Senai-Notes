package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.CadastroUsuarioDTO;
import com.senai_notes.senai_notes.dto.ListarUsuarioDTO;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository repo) {
        usuarioRepository = repo;
    }

    public Usuario cadastrarUsuario(Usuario usu) {
     return usuarioRepository.save(usu);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public CadastroUsuarioDTO cadastrar(CadastroUsuarioDTO usuarioDto) {
        // 1. Usa o 'usuarioId' da DTO para buscar a ENTIDADE 'Usuario' completa no banco.
        //Integer id_usuario = usuarioDto.getUsuarioId();
        //Usuario usuario = usuarioRepository.findById(usuarioId)
        //        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        // 2. Cria uma nova instância da ENTIDADE 'Usuario', que será salva.
        Usuario novoUsuario = new Usuario();

        // 3. Mapeia os dados da DTO e da entidade buscada para a nova entidade.
        novoUsuario.setNome(usuarioDto.getNome());
        novoUsuario.setEmail(usuarioDto.getEmail()); // Associa o usuário completo que buscamos
        novoUsuario.setSenha(usuarioDto.getSenha());
        novoUsuario.setDataCriacao(OffsetDateTime.now());

        // 4. Salva a entidade preenchida no banco.
        usuarioRepository.save(novoUsuario);

        // 5. Retorna a DTO original para confirmar os dados que foram enviados.
        return usuarioDto;
    }



        public List<ListarUsuarioDTO> listarTodos() {
        // 1. Busca todas as entidades do banco
        List<Usuario> usuarios = usuarioRepository.findAll();

        // 2. Mapeia a lista de Entidades para uma lista de DTOs
        return usuarios.stream()
                // Usa o método auxiliar de conversão
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

        private ListarUsuarioDTO converterParaListagemDTO(Usuario usuario) {
            ListarUsuarioDTO dto = new ListarUsuarioDTO();

            // Mapeamento campo a campo
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setDataCriacao(usuario.getDataCriacao());

            return dto;
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