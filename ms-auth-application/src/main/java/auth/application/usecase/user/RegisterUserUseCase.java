package auth.application.usecase.user;

import auth.application.facades.MessageFacade;
import auth.application.messages.UserCreatedMessage;
import auth.application.usecase.UseCase;
import auth.application.usecase.user.input.RegisterInput;
import auth.application.usecase.user.output.RegisterOutput;
import auth.domain.exception.ConflictException;
import auth.domain.gateway.UserGateway;
import auth.domain.model.Address;
import auth.domain.model.User;
import auth.domain.service.PasswordEncoderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserUseCase extends UseCase<RegisterInput, RegisterOutput> {

    private final UserGateway userGateway;
    private final PasswordEncoderService passwordEncoder;
    private final MessageFacade messageFacade;

    public RegisterUserUseCase(UserGateway userGateway, PasswordEncoderService passwordEncoder, MessageFacade messageFacade) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
        this.messageFacade = messageFacade;
    }


    @Override
    public RegisterOutput execute(RegisterInput input) {
        validate(input);
        verifyIfUserExists(input.username());
        verifyIfEmailExists(input.email());

        final String passwordEncoded = encoder(input.password());

        final List<Address> addresses = input.addresses()
                .stream()
                .map(addressInput -> Address.create(
                        addressInput.road(),
                        addressInput.number(),
                        addressInput.city(),
                        addressInput.state(),
                        addressInput.zipCode()
                ))
                .toList();

        final User domain = User.create(
                input.username(),
                passwordEncoded,
                input.name(),
                input.email()
        );

        final User user = userGateway.save(domain);

        messageFacade.sendMessage(toMessage(user, input, addresses));

        return new RegisterOutput(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }

    private void verifyIfUserExists(String username) {
        if (userGateway.existsByUsername(username)) {
            throw new ConflictException("Username already exists");
        }
    }

    private void verifyIfEmailExists(String email) {
        if (userGateway.existsByEmail(email)) {
            throw new ConflictException("Email already exists");
        }
    }

    private UserCreatedMessage toMessage(User user, RegisterInput input, List<Address> addresses) {
        return new UserCreatedMessage(
                user.getId().toString(),
                input.document(),
                user.getEmail(),
                user.getName(),
                input.phone(),
                user.getDateCreated().toString(),
                addresses.stream()
                        .map(UserCreatedMessage.Address::from)
                        .toList()
        );
    }

    @Override
    protected void validate(RegisterInput input) {
        if (input.username() == null || input.username().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (input.password() == null || input.password().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}
