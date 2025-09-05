package auth.web.controller;

import auth.application.usecase.user.LoginUserUseCase;
import auth.application.usecase.user.RegisterUserUseCase;
import auth.application.usecase.user.input.AddressInput;
import auth.application.usecase.user.input.LoginInput;
import auth.application.usecase.user.input.RegisterInput;
import auth.application.usecase.user.output.AuthOutput;
import auth.application.usecase.user.output.RegisterOutput;
import auth.web.controller.request.LoginRequest;
import auth.web.controller.request.RegisterRequest;
import auth.web.controller.response.LoginResponse;
import auth.web.controller.response.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        final LoginInput input = LoginInput.builder()
                .username(request.username())
                .password(request.password())
                .build();

        final AuthOutput output = loginUserUseCase.execute(input);

        final LoginResponse response = LoginResponse.of(output.userId(), output.username());

        return ResponseEntity
                .ok()
                .header("Authorization", "Bearer " + output.token().getValue())
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request,
            HttpServletRequest servletRequest
    ) {

        final List<AddressInput> addressInput = request.addresses().stream()
                .map(address -> AddressInput.builder()
                        .road(address.road())
                        .number(address.number())
                        .city(address.city())
                        .state(address.state())
                        .zipCode(address.zipCode())
                        .build())
                .toList();

        final RegisterInput input = RegisterInput
                .builder()
                .username(request.username())
                .password(request.password())
                .name(request.name())
                .email(request.email())
                .document(request.document())
                .dateOfBirth(request.dateOfBirth())
                .phone(request.phone())
                .addresses(addressInput)
                .build();

        final RegisterOutput output = registerUserUseCase.execute(input);

        final RegisterResponse response = RegisterResponse.of(
                output.id(),
                output.username(),
                output.email()
        );

        final String baseUrl = servletRequest
                .getRequestURL()
                .toString()
                .replace(servletRequest.getRequestURI(), "") + "/api/v1/users/" + output.id();
        final URI location = URI.create(baseUrl);

        return ResponseEntity.created(location).body(response);
    }
}
