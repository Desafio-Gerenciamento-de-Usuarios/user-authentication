package auth.application.usecase.user.output;

import auth.domain.model.Token;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthOutput(
        UUID userId,
        String name,
        Token token
) {
}
