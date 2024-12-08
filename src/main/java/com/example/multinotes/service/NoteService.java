package com.example.multinotes.service;

import com.example.multinotes.config.TenantContext;
import com.example.multinotes.model.Note;
import com.example.multinotes.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private NoteRepository noteRepository;

    private SimpMessagingTemplate messagingTemplate;

    public Note createNote(Note note) {
        note.setCreatedBy(TenantContext.getTenantId());
        Note savedNote = noteRepository.save(note);

        // Notify clients with the new note
        messagingTemplate.convertAndSend("/topic/notes", savedNote);

        return savedNote;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findFirstByAllowedToReadTenants(TenantContext.getTenantId());
    }

    public Note getNoteById(String id) {
        return noteRepository.findByIdAndCreatedBy(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public Note updateNote(String id, Note updatedNote) {
        Note existingNote = getNoteById(id);
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setTags(updatedNote.getTags());
        Note savedNote = noteRepository.save(existingNote);

        // Notify clients with updated note content
        messagingTemplate.convertAndSend("/topic/notes/" + id, savedNote);

        return savedNote;
    }

    public void deleteNote(String id) {
        Note note = getNoteById(id);
        noteRepository.delete(note);

        // Notify subscribers about the deletion
        messagingTemplate.convertAndSend("/topic/notes/" + id, "deleted");
    }
}
