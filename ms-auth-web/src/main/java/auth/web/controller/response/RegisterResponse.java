package auth.web.controller.response;

import java.util.UUID;


public record RegisterResponse(
        UUID id,
        String username,
        String email
) {
    public static RegisterResponse of(UUID id, String username, String email) {
        return new RegisterResponse(id, username, email);
    }
}
