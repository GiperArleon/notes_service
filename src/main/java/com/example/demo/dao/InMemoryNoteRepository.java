package com.example.demo.dao;

import com.example.demo.dto.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryNoteRepository implements NoteRepository {
    private final Map<String, Note> notes = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> tagIndex = new ConcurrentHashMap<>();

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            note.setId(UUID.randomUUID().toString());
        }
        notes.put(note.getId(), note);

        if (note.getTags() != null) {
            for (String tag : note.getTags()) {
                tagIndex.computeIfAbsent(tag, k -> ConcurrentHashMap.newKeySet())
                        .add(note.getId());
            }
        }
        return note;
    }

    @Override
    public Optional<Note> findById(String id) {
        return Optional.ofNullable(notes.get(id));
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public List<Note> findByTag(String tag) {
        Set<String> noteIds = tagIndex.getOrDefault(tag, Collections.emptySet());
        return noteIds.stream()
                .map(notes::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        Note removed = notes.remove(id);
        if (removed != null && removed.getTags() != null) {
            for (String tag : removed.getTags()) {
                Set<String> ids = tagIndex.get(tag);
                if (ids != null) {
                    ids.remove(id);
                    if (ids.isEmpty()) {
                        tagIndex.remove(tag);
                    }
                }
            }
        }
    }

    @Override
    public boolean existsById(String id) {
        return notes.containsKey(id);
    }

    @Override
    public void clear() {
        notes.clear();
        tagIndex.clear();
    }
}
