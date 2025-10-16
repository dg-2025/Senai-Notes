package com.senaiNotes.senainotes.Repository;

import com.senaiNotes.senainotes.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByNome(String nome);
    List<Tag> findByUsuarioId(Integer usuarioId); // ✅ agora o Spring entende
}

