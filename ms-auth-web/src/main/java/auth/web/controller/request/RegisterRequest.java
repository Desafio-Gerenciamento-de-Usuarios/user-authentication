package auth.web.controller.request;

public record RegisterRequest(
        String username,
        String password,
        String name,
        String email
) {
}
