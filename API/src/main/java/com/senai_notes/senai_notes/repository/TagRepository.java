package com.senai_notes.senai_notes.repository;


import com.senai_notes.senai_notes.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    // Listar tags por e-mail do usuário
    List<Tag> findByUsuarioEmail(String email);

}

