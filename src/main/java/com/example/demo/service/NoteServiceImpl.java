package com.example.demo.service;

import com.example.demo.dao.NoteRepository;
import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import com.example.demo.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public ResponseEntity<Note> createNote(NoteRequest request) {
        var note = noteRepository.save(noteMapper.toNote(request));
        return noteMapper.toNoteResponse(note);
    }

    @Override
    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getNotesByTag(String tag) {
        return noteRepository.findByTag(tag);
    }

    @Override
    public Note updateNote(String id, NoteRequest request) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        existingNote.setTitle(request.getTitle());
        existingNote.setContent(request.getContent());
        existingNote.setTags(request.getTags() != null ? new HashSet<>(request.getTags()) : new HashSet<>());

        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }
}
