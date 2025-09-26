package com.senai_notes.senai_notes.repository;


import com.senai_notes.senai_notes.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  TagRepository extends JpaRepository<Tag,Integer> {
}
