package auth.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String username;
    private String name;
    private String email;
    private String passwordHash;
    private LocalDateTime dateCreated;

    public User(UUID id, String username, String name, String passwordHash, String email, LocalDateTime dateCreated) {
        validate(id, username, passwordHash, email);
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.dateCreated = dateCreated;
    }

    public User(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static User create(String username, String passwordHash, String name, String email) {
        final UUID id = UUID.randomUUID();
        final LocalDateTime createDate = LocalDateTime.now();
        return new User(id, username, name, passwordHash, email, createDate);
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash);
    }
}

