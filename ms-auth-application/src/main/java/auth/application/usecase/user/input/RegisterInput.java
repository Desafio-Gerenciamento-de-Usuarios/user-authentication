package auth.application.usecase.user.input;

import lombok.Builder;

import java.util.List;

@Builder
public record RegisterInput(
        String username,
        String password,
        String name,
        String email,
        String document,
        String dateOfBirth,
        String phone,
        List<AddressInput> addresses
) {
}
