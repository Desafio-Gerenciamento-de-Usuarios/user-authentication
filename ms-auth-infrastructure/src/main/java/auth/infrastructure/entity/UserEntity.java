package auth.infrastructure.entity;

import auth.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "USUARIOS")
public class UserEntity {

    @Id
    @Column(name = "ID_USUARIO")
    private UUID id;

    @Column(name = "USUARIO_LOGIN", unique = true, nullable = false)
    private String username;

    @Column(name = "NOME_USUARIO", nullable = false)
    private String name;

    @Column(name = "SENHA", nullable = false)
    private String passwordHash;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDateTime dateCreated;

    public UserEntity() {
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setDateCreated(user.getDateCreated());
        return entity;
    }

    public User toDomain() {
        return new User(
                this.getId(),
                this.getUsername(),
                this.getName(),
                this.getPasswordHash(),
                this.getEmail(),
                this.getDateCreated()
        );
    }
}
