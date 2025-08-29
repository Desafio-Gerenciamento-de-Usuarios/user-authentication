package auth.application.usecase.user.input;

import lombok.Builder;

@Builder
    public record AddressInput(
            String road,
            String number,
        String city,
        String state,
        String zipCode
) {
}
