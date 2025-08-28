package auth.application.usecase;

public abstract class UseCase<I, O> {
    public abstract O execute(I input);

    protected abstract void validate(I input);
}
