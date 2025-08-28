package auth.infrastructure.repository;

import auth.domain.model.User;
import auth.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
