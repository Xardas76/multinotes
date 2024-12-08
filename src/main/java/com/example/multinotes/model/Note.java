package com.example.multinotes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "notes")
@Getter
@Setter
public class Note {

    @Id
    private String id;

    private String title;

    private String content;

    private UUID createdBy;

    private List<UUID> allowedToReadTenants;

    private List<UUID> allowedToWriteTenants;

    private List<String> tags;

    @Version
    private Long version;

}
