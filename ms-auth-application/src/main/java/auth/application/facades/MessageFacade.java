package auth.application.facades;

import auth.application.messages.UserCreatedMessage;

public interface MessageFacade {
    void sendMessage(UserCreatedMessage message);
}
