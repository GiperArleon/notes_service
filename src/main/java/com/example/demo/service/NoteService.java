package com.example.demo.service;

import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface NoteService {
    ResponseEntity<Note> createNote(NoteRequest request);
    ResponseEntity<Note> getNoteById(String id);
    ResponseEntity<List<Note>> getNotesByTag(String tag);
    Note updateNote(String id, NoteRequest request);
    void deleteNote(String id);
}
