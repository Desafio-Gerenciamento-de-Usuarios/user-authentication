package auth.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String username;
    private String name;
    private String email;
    private String passwordHash;

    public User(UUID id, String username, String name, String passwordHash, String email) {
        validate(id, username, passwordHash, email);
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
    }

    public User(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static User create(String username, String passwordHash, String name, String email) {
        final UUID id = UUID.randomUUID();
        return new User(id, username, name, passwordHash, email);
    }

    private void validate(UUID id, String username, String passwordHash, String email) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Email is not valid");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash);
    }
}

