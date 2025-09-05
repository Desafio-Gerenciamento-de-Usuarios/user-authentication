package auth.application.usecase.user.output;

import java.util.UUID;

public record RegisterOutput(
        UUID id,
        String username,
        String email
) {
}
