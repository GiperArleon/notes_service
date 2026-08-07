package com.example.demo.service;

import com.example.demo.dao.NoteRepository;
import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.UtilData.NOTE_ID;
import static com.example.demo.utils.UtilData.NOTE_TITLE;
import static com.example.demo.utils.UtilData.TAG_ONE;
import static com.example.demo.utils.UtilData.TAG_THREE;
import static com.example.demo.utils.UtilData.UPDATE_CONTENT;
import static com.example.demo.utils.UtilData.UPDATE_TITLE;
import static com.example.demo.utils.UtilData.WRONG_NOTE_ID;
import static com.example.demo.utils.UtilData.noteOf;
import static com.example.demo.utils.UtilData.noteRequestOf;
import static com.example.demo.utils.UtilData.updateRequestOf;
import static com.example.demo.utils.UtilData.updatedNoteOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    private Note testNote;

    private NoteRequest testRequest;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        testNote = noteOf();
        testRequest = noteRequestOf();
    }

    @Test
    void createNote_ShouldReturnCreatedNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        Note result = noteService.createNote(testRequest);

        assertNotNull(result);
        assertEquals(testNote.getTitle(), result.getTitle());
        assertEquals(testNote.getContent(), result.getContent());
        assertEquals(testNote.getTags(), result.getTags());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void getNoteById_WhenNoteExists_ShouldReturnNote() {
        when(noteRepository.findById(NOTE_ID)).thenReturn(Optional.of(testNote));

        Note result = noteService.getNoteById(NOTE_ID);

        assertNotNull(result);
        assertEquals(NOTE_ID, result.getId());
        assertEquals(NOTE_TITLE, result.getTitle());
        verify(noteRepository, times(1)).findById(NOTE_ID);
    }

    @Test
    void getNoteById_WhenNoteNotFound_ShouldThrowException() {
        when(noteRepository.findById(WRONG_NOTE_ID)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> noteService.getNoteById(WRONG_NOTE_ID));

        assertEquals("Note not found with id: 999", exception.getMessage());
        verify(noteRepository, times(1)).findById(WRONG_NOTE_ID);
    }

    @Test
    void getAllNotes_ShouldReturnAllNotes() {
        List<Note> notes = Arrays.asList(testNote, new Note());
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getAllNotes();

        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getNotesByTag_ShouldReturnFilteredNotes() {
        List<Note> notes = Collections.singletonList(testNote);
        when(noteRepository.findByTag(TAG_ONE)).thenReturn(notes);

        List<Note> result = noteService.getNotesByTag(TAG_ONE);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getTags().contains(TAG_ONE));
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
