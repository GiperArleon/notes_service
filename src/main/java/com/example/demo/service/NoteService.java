package com.example.demo.service;

import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import java.util.List;

public interface NoteService {
    Note createNote(NoteRequest request);
    Note getNoteById(String id);
    List<Note> getAllNotes();
    List<Note> getNotesByTag(String tag);
    Note updateNote(String id, NoteRequest request);
    void deleteNote(String id);
}
