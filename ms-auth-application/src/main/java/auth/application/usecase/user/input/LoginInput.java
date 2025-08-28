package auth.application.usecase.user.input;

import lombok.Builder;

@Builder
public record LoginInput(
        String username,
        String password
) {
}
