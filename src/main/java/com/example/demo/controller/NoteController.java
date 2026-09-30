package com.example.demo.controller;

import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import com.example.demo.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    /*
     * 1
     */
    @PostMapping
    public ResponseEntity<Note> createNote(
            @Valid @RequestBody NoteRequest request
    ) {
        return noteService.createNote(request);
    }

    /*
     * 2
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(
            @PathVariable String id
    ) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(
            @RequestParam(required = false) String tag
    ) {
        return noteService.getNotesByTag(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable String id,
            @Valid @RequestBody NoteRequest request
    ) {
        try {
            Note updated = noteService.updateNote(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
              .notFound()
              .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable String id
    ) {
        noteService.deleteNote(id);

        return ResponseEntity
          .noContent()
          .build();
    }
}
