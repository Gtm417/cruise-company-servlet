package ua.training.exception;

public class ExcursionNotFound extends Exception {
    private long id;

    public ExcursionNotFound(long id) {
        this.id = id;
    }

    public ExcursionNotFound(String message, long id) {
        super(message + id);
        this.id = id;
    }
}
