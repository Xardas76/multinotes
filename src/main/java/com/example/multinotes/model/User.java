package com.example.multinotes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends Tenant {

    private String fullName;

    private String username;

    private String organizationId;

    private List<Role> roles;

}
