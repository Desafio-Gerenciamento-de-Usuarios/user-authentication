package auth.infrastructure.gateway.messaging.producer;

import auth.application.messages.UserCreatedMessage;
import auth.infrastructure.gateway.messaging.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProducer extends Message<UserCreatedMessage> {

    private final AmqpTemplate amqpTemplate;
    private final String queueName;

    public CreateUserProducer(AmqpTemplate amqpTemplate, @Value("${amqp.queue.user-created}") String queueName) {
        this.amqpTemplate = amqpTemplate;
        this.queueName = queueName;
    }

    @Override
    public void send(UserCreatedMessage message) {
        this.amqpTemplate.convertAndSend(this.queueName, message);
    }
}
