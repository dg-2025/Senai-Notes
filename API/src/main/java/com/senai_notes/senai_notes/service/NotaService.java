package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.dto.NotaRequest;
import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.models.Tag;
import com.senai_notes.senai_notes.models.Usuario;
import com.senai_notes.senai_notes.repository.NotaRepository;
import com.senai_notes.senai_notes.repository.TagRepository;
import com.senai_notes.senai_notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotaService {

    //injeção de dependencias
    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaService(NotaRepository notaRepository, TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }


    //listar
    public List<Nota> listarNotas() {
        return notaRepository.findAll();
    }

    //listar
    public List<Tag> listarnotaEmail(String email) {
        return tagRepository.findByUsuarioEmail(email);
    }

    //buscar por id
    public Nota bucarPorId(int id){
        return notaRepository.findById(id).orElse(null);
    }

    //adicionar nota
    public Nota adiconarNota(NotaRequest dto){

        Tag tagAssocioda = tagRepository.findById(dto.getIdTag()).orElse(null);
        if(tagAssocioda == null){
            return null;
        }

        Usuario usuarioAssosciada = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if(usuarioAssosciada == null){
            return null;
        }

        Nota nota = new Nota();

        nota.setDescricao(dto.getDescricao());
        nota.setTitulo(dto.getTitulo());
        nota.setImagem(dto.getImagem());
        nota.setIdTag(tagAssocioda);
        nota.setIdUsuario(usuarioAssosciada);

        return notaRepository.save(nota);
    }

    //atualizar nota / editar nota
    public Nota atualizarNota(NotaRequest dto, int id){
        Tag tagAssocioda = tagRepository.findById(dto.getIdTag()).orElse(null);
        if(tagAssocioda == null){
            return null;
        }

        Usuario usuarioAssosciada = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        if(usuarioAssosciada == null){
            return null;
        }

        Nota notaExistente = this.bucarPorId(id);
        if (notaExistente == null){
            return null;
        }

        notaExistente.setDescricao(dto.getDescricao());
        notaExistente.setTitulo(dto.getTitulo());
        notaExistente.setImagem(dto.getImagem());
        notaExistente.setIdTag(tagAssocioda);
        notaExistente.setIdUsuario(usuarioAssosciada);



        return notaRepository.save(notaExistente);
    }

    //deletar nota
    public Nota removerNota(int id){
        Nota notaExistente = this.bucarPorId(id);
        if (notaExistente == null){
            return null;
        }
        notaRepository.delete(notaExistente);
        return notaExistente;
    }

}

