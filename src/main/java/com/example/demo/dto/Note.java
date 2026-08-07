package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Set<String> tags = new HashSet<>();

    public Note(String title, String content, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags != null ? tags : new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }
}
