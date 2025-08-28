package auth.application.usecase.token;

import auth.application.usecase.UseCase;
import auth.domain.exception.InvalidTokenException;
import auth.domain.exception.TokenExpiredException;
import auth.domain.gateway.TokenGateway;
import auth.domain.model.Token;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenUseCase extends UseCase<String, String> {

    private final TokenGateway tokenGateway;

    public RefreshTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    @Override
    public String execute(String token) {
        validate(token);

        try {
            final Token renewedToken = tokenGateway.refreshToken(token);
            return renewedToken.getValue();
        } catch (InvalidTokenException | TokenExpiredException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao renovar token", e);
        }
    }

    @Override
    protected void validate(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("O token não pode ser null ou vazio");
        }
    }
}