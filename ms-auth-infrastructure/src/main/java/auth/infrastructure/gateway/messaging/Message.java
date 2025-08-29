package auth.infrastructure.gateway.messaging;

public abstract class Message<T> {
    public abstract void send(T message);
}
