package auth.application.usecase.user.input;

import lombok.Builder;

@Builder
public record RegisterInput(
        String username,
        String password,
        String name,
        String email
) {
}
