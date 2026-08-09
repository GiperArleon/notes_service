package com.example.demo.service;

import com.example.demo.dao.NoteRepository;
import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import com.example.demo.error.NoteNotFoundException;
import com.example.demo.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Note> getNoteById(String id) {
        return noteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Note>> getNotesByTag(String tag) {
        List<Note> notes = Optional.ofNullable(tag)
                .filter(t -> !t.trim().isEmpty())
                .map(noteRepository::findByTag)
                .orElseGet(noteRepository::findAll);

        return noteMapper.toNotesResponse(notes);
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
            throw new NoteNotFoundException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }
}
