package auth.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Token {
    private final UUID id;
    private final String value;
    private final Instant expiresAt;

    public Token(String value, Instant expiresAt) {
        this.id = UUID.randomUUID();
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public UUID getId() { return id; }

    public String getValue() { return value; }
}

