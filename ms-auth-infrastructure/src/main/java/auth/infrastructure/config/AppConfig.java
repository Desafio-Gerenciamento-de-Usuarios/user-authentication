package auth.infrastructure.config;


import auth.application.usecase.token.RefreshTokenUseCase;
import auth.application.usecase.user.LoginUserUseCase;
import auth.application.usecase.user.RegisterUserUseCase;
import auth.domain.gateway.TokenGateway;
import auth.domain.gateway.UserGateway;
import auth.domain.service.PasswordEncoderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserGateway userGateway, PasswordEncoderService passwordEncoder) {
        return new RegisterUserUseCase(userGateway, passwordEncoder);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(UserGateway userGateway, TokenGateway tokenGateway, PasswordEncoderService passwordEncoderService) {
        return new LoginUserUseCase(userGateway, tokenGateway, passwordEncoderService);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(TokenGateway tokenGateway) {
        return new RefreshTokenUseCase(tokenGateway);
    }
}
