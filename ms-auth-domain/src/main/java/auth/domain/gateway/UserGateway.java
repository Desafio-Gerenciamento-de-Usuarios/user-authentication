package auth.domain.gateway;

import auth.domain.model.User;

import java.util.Optional;

public interface UserGateway {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    Optional<User> findByUser(String username);
}
