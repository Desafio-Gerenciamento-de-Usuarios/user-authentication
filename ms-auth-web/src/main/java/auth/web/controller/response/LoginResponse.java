package auth.web.controller.response;

import java.util.UUID;

public record LoginResponse(
        UUID idUser,
        String username
) {
    public static LoginResponse of(UUID uuid, String username) {
        return new LoginResponse(uuid, username);
    }
}
