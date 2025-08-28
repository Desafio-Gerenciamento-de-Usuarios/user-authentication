package auth.application.usecase.user;

import auth.application.usecase.UseCase;
import auth.application.usecase.user.input.LoginInput;
import auth.application.usecase.user.output.AuthOutput;
import auth.domain.exception.AccessDeniedException;
import auth.domain.gateway.TokenGateway;
import auth.domain.gateway.UserGateway;
import auth.domain.model.Token;
import auth.domain.model.User;
import auth.domain.service.PasswordEncoderService;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase extends UseCase<LoginInput, AuthOutput> {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;
    private final PasswordEncoderService passwordEncoderService;

    public LoginUserUseCase(UserGateway userGateway, TokenGateway tokenGateway, PasswordEncoderService passwordEncoderService) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public AuthOutput execute(LoginInput input) {
        validate(input);
        User user = findByUser(input.username());

        verifyPassword(input.password(), user.getPasswordHash());

        Token token = tokenGateway.generateToken(user);

        return AuthOutput.builder()
                .userId(user.getId())
                .name(user.getName())
                .token(token)
                .build();
    }

    private void verifyPassword(String password, String passwordHash) {
        if (!passwordEncoderService.matches(password, passwordHash)) {
            throw new AccessDeniedException("Usuário ou senha inválido");
        }
    }

    private User findByUser(String username) {
        return userGateway.findByUser(username).orElseThrow(
                () -> new AccessDeniedException("Usuário ou senha inválido")
        );
    }

    @Override
    public void validate(LoginInput input) {
        if (input.username() == null || input.username().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (input.password() == null || input.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

}
