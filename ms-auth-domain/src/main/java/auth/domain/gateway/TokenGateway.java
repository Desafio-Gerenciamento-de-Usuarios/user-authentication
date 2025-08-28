package auth.domain.gateway;

import auth.domain.model.Token;
import auth.domain.model.User;

public interface TokenGateway {
    Token generateToken(User user);

    Token refreshToken(String token);
}
