package ua.training.exception;

public class ExcursionNotFound extends Exception {
    private long id;

    public ExcursionNotFound(String message, long id) {
        super(message + id);
        this.id = id;
    }
}
