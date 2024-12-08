package com.example.multinotes.service;

import com.example.multinotes.model.Note;
import com.example.multinotes.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@AllArgsConstructor
@Component
public class webSocketSubscriptionHandler {

    public static final String TOPIC_NOTES = "/topic/notes/";
    private NoteRepository noteRepository;
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSubscriptionEvent(SessionSubscribeEvent event) {
        String destination = event.getMessage().getHeaders().get("simpDestination", String.class);

        if (destination != null && destination.startsWith(TOPIC_NOTES)) {
            String noteId = destination.substring(TOPIC_NOTES.length());
            Note note = noteRepository.findById(noteId)
                    .orElseThrow(() -> new RuntimeException("Note not found"));

            // Send the latest note content to the subscriber
            messagingTemplate.convertAndSend(destination, note);
        }
    }

}
