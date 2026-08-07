package com.example.demo.dao;

import com.example.demo.dto.Note;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository {
    Note save(Note note);
    Optional<Note> findById(String id);
    List<Note> findAll();
    List<Note> findByTag(String tag);
    void deleteById(String id);
    boolean existsById(String id);
    void clear();
}
