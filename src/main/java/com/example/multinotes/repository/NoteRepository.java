package com.example.multinotes.repository;

import com.example.multinotes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findFirstByAllowedToReadTenants(UUID tenantId);

    Optional<Note> findByIdAndCreatedBy(String id, UUID createdBy);

}
