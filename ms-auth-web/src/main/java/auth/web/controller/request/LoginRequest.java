package auth.web.controller.request;

public record LoginRequest(
        String username,
        String password
) {
}
