package com.example.demo.service;

import com.example.demo.dao.NoteRepository;
import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import com.example.demo.mapper.NoteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.example.demo.utils.UtilData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    private Note testNote;
    private NoteRequest testRequest;
    private ResponseEntity<Note> testResponse;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteMapper noteMapper;
    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        testNote = noteOf();
        testRequest = noteRequestOf();
        testResponse = noteResponseOf();
    }

    @Test
    void createNote_ShouldReturnCreatedNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);
        when(noteMapper.toNote(any(NoteRequest.class))).thenReturn(testNote);
        when(noteMapper.toNoteResponse(any(Note.class))).thenReturn(testResponse);

        ResponseEntity<Note> result = noteService.createNote(testRequest);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(testNote.getTitle(), result.getBody().getTitle());
        assertEquals(testNote.getContent(), result.getBody().getContent());
        assertEquals(testNote.getTags(), result.getBody().getTags());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void getNoteById_WhenNoteExists_ShouldReturnNote() {
        when(noteRepository.findById(NOTE_ID)).thenReturn(Optional.of(testNote));

        ResponseEntity<Note> result = noteService.getNoteById(NOTE_ID);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(NOTE_ID, result.getBody().getId());
        assertEquals(NOTE_TITLE, result.getBody().getTitle());
        verify(noteRepository, times(1)).findById(NOTE_ID);
    }

    @Test
    void getAllNotes_ShouldReturnAllNotes() {
        when(noteRepository.findAll()).thenReturn(notesOf());
        when(noteMapper.toNotesResponse(any())).thenReturn(notesResponseOf(notesOf()));

        ResponseEntity<List<Note>> result = noteService.getNotesByTag(null);

        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getNotesByTag_ShouldReturnFilteredNotes() {
        List<Note> notes = Collections.singletonList(testNote);
        when(noteRepository.findByTag(TAG_ONE)).thenReturn(notes);
        when(noteMapper.toNotesResponse(any())).thenReturn(notesResponseOf(notes));

        ResponseEntity<List<Note>> result = noteService.getNotesByTag(TAG_ONE);

        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertTrue(result.getBody().get(0).getTags().contains(TAG_ONE));
        verify(noteRepository, times(1)).findByTag(TAG_ONE);
    }

    @Test
    void updateNote_WhenNoteExists_ShouldUpdateAndReturn() {
        NoteRequest updateRequest = updateRequestOf();
        Note updatedNote = updatedNoteOf();

        when(noteRepository.findById(NOTE_ID)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);

        Note result = noteService.updateNote(NOTE_ID, updateRequest);

        assertNotNull(result);
        assertEquals(UPDATE_TITLE, result.getTitle());
        assertEquals(UPDATE_CONTENT, result.getContent());
        assertTrue(result.getTags().contains(TAG_THREE));
        verify(noteRepository, times(1)).findById(NOTE_ID);
        verify(noteRepository, times(1)).save(any(Note.class));
    }
}
