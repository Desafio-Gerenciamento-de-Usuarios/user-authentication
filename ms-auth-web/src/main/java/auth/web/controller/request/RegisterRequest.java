package auth.web.controller.request;

import java.util.List;

public record RegisterRequest(
        String username,
        String password,
        String name,
        String email,
        String document,
        String dateOfBirth,
        String phone,
        List<AddressRequest> addresses
) {
}
