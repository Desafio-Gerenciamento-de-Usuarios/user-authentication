package auth.infrastructure.fecade;

import auth.application.facades.MessageFacade;
import auth.application.messages.UserCreatedMessage;
import auth.infrastructure.gateway.messaging.producer.CreateUserProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFacadeImpl implements MessageFacade {

    private final CreateUserProducer createUserProducer;

    @Override
    public void sendMessage(UserCreatedMessage message) {
        createUserProducer.send(message);
    }
}
