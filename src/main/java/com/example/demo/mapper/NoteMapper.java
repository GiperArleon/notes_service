package com.example.demo.mapper;

import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.HashSet;

@Component
public class NoteMapper {

    public Note toNote(NoteRequest request) {
        return new Note(
                request.getTitle(),
                request.getContent(),
                request.getTags() != null ?
                        new HashSet<>(request.getTags()) :
                        new HashSet<>()
        );
    }

    public ResponseEntity<Note> toNoteResponse(Note note) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(note);
    }
}
