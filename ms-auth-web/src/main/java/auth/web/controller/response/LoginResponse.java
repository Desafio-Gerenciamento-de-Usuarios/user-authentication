package auth.web.controller.response;

import java.util.UUID;

public record LoginResponse(
        UUID idUser,
        String name
) {
    public static LoginResponse of(UUID uuid, String name) {
        return new LoginResponse(uuid, name);
    }
}
