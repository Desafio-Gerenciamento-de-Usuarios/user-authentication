package auth.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "auth.web",
        "auth.application",
        "auth.infrastructure"
})
@EntityScan("auth.infrastructure.entity")
@EnableJpaRepositories("auth.infrastructure.gateway.repository")
public class ApplicationConfig {
}
