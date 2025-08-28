package auth.infrastructure.entity;

import auth.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "USUARIO")
public class UserEntity {

    @Id
    @Column(name = "ID_USER")
    private UUID id;

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String username;

    @Column(name = "NOME_USUARIO", nullable = false)
    private String name;

    @Column(name = "SENHA", nullable = false)
    private String passwordHash;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    public UserEntity() {
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return entity;
    }

    public User toDomain() {
        return new User(
                this.getId(),
                this.getUsername(),
                this.getName(),
                this.getPasswordHash(),
                this.getEmail()
        );
    }
}
