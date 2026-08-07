package com.example.demo.utils;

import com.example.demo.dto.Note;
import com.example.demo.dto.NoteRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UtilData {
    public static final String NOTE_ID = "1";
    public static final String WRONG_NOTE_ID = "999";
    public static final String NOTE_TITLE = "Test Title";
    public static final String NOTE_CONTENT = "Test Content";
    public static final String TAG_ONE = "work";
    public static final String TAG_TWO = "important";
    public static final String TAG_THREE = "personal";
    public static final String UPDATE_TITLE = "Updated Title";
    public static final String UPDATE_CONTENT = "Updated Content";

    public static Set<String> tagsOf() {
        return new HashSet<>(Arrays.asList(TAG_ONE, TAG_TWO));
    }

    public static Set<String> updatedTagsOf() {
        return new HashSet<>(Collections.singletonList(TAG_THREE));
    }

    public static Note noteOf() {
        return new Note(
                NOTE_ID,
                NOTE_TITLE,
                NOTE_CONTENT,
                java.time.LocalDateTime.now(),
                tagsOf()
        );
    }

    public static Note updatedNoteOf() {
        return new Note(
                NOTE_ID,
                UPDATE_TITLE,
                UPDATE_CONTENT,
                java.time.LocalDateTime.now(),
                updatedTagsOf()
        );
    }

    public static NoteRequest noteRequestOf() {
        return new NoteRequest(
                NOTE_TITLE,
                NOTE_CONTENT,
                tagsOf()
        );
    }

    public static NoteRequest updateRequestOf() {
        return new NoteRequest(
                UPDATE_TITLE,
                UPDATE_CONTENT,
                updatedTagsOf()
        );
    }
}
