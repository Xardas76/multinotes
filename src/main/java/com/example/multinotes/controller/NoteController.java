package com.example.multinotes.controller;

import com.example.multinotes.model.Note;
import com.example.multinotes.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@AllArgsConstructor
public class NoteController {

    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.ok(createdNote);
    }

    // Get all notes for the tenant
    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // Get a single note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    // Update a note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(id, note);

        return ResponseEntity.ok(updatedNote);
    }

    // Delete a note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);

        return ResponseEntity.noContent().build();
    }
}
