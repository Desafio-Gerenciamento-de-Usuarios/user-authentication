package auth.infrastructure.gateway;

import auth.domain.exception.AccessDeniedException;
import auth.domain.gateway.UserGateway;
import auth.domain.model.User;
import auth.infrastructure.entity.UserEntity;
import auth.infrastructure.repository.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGatewayImpl implements UserGateway {

    private final JpaUserRepository jpaUserRepository;

    public UserGatewayImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        final UserEntity entity = UserEntity.toEntity(user);
        return jpaUserRepository.save(entity).toDomain();
    }

    @Override
    public Optional<User> findByUser(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(UserEntity::toDomain);
    }


}
