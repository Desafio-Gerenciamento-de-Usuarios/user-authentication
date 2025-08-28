package auth.web.controller;

import auth.application.usecase.token.RefreshTokenUseCase;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {


    private final RefreshTokenUseCase refreshTokenUseCase;

    public TokenController(RefreshTokenUseCase refreshTokenUseCase) {
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");

        String renewedToken = refreshTokenUseCase.execute(token);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + renewedToken)
                .build();
    }
}
