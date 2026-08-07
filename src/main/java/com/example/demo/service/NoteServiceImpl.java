package com.example.demo.service;

import com.example.demo.dao.NoteRepository;
import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public Note createNote(NoteRequest request) {
        Note note = new Note(
                request.getTitle(),
                request.getContent(),
                request.getTags() != null ? new HashSet<>(request.getTags()) : new HashSet<>()
        );
        return noteRepository.save(note);
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
