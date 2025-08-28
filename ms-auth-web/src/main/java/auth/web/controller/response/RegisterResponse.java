package auth.web.controller.response;

import java.util.UUID;


public record RegisterResponse(
        UUID id,
        String name,
        String email
) {
    public static RegisterResponse of(UUID id, String name, String email) {
        return new RegisterResponse(id, name, email);
    }
}
