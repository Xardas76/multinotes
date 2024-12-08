package com.example.multinotes.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
public abstract class Tenant {

    @Id
    private UUID id;

}