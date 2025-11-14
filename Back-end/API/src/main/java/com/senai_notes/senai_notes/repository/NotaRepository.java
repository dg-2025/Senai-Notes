package com.senai_notes.senai_notes.repository;
import com.senai_notes.senai_notes.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface  NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByIdUsuarioEmail(String Email);
    List<Nota> findByIdUsuario_id(Integer id);
}
