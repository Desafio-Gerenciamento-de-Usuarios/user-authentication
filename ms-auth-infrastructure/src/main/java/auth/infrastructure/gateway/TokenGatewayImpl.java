package auth.infrastructure.gateway;

import auth.domain.gateway.TokenGateway;
import auth.domain.model.Token;
import auth.domain.model.User;
import auth.infrastructure.security.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class TokenGatewayImpl implements TokenGateway {

    private final JwtProvider tokenProvider;

    public TokenGatewayImpl(JwtProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Token generateToken(User user) {
        return tokenProvider.createToken(user);
    }

    @Override
    public Token refreshToken(String token) {
        return tokenProvider.refreshToken(token);
    }
}
