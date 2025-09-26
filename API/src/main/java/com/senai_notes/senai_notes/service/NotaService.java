package com.senai_notes.senai_notes.service;

import com.senai_notes.senai_notes.models.Nota;
import com.senai_notes.senai_notes.repository.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//
@Service
public class NotaService {

    //injeção de dependencias
    private final NotaRepository notaRepository;
    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }


    //listar
    public List<Nota> listarNotas() {
        return notaRepository.findAll();
    }

    //buscar por id
    public Nota bucarPorId(int id){
        return notaRepository.findById(id).orElse(null);
    }

    //adicionar nota
    public Nota adiconarNota(Nota newNota){
        return notaRepository.save(newNota);
    }

    //atualizar nota / editar nota
    public Nota atualizarNota(Nota updateNota, int id){
        Nota notaExistente = this.bucarPorId(id);
        if (notaExistente == null){
            return null;
        }
        notaExistente.setTitulo(updateNota.getTitulo());
        notaExistente.setDescricao(updateNota.getDescricao());
        notaExistente.setArquivado(updateNota.getArquivado());
        notaExistente.setUltimaEdicao(updateNota.getUltimaEdicao());
        notaExistente.setImagem(updateNota.getImagem());
        notaExistente.setIdTag(updateNota.getIdTag());
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
