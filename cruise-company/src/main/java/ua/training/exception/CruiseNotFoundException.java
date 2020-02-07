package ua.training.exception;

public class CruiseNotFoundException extends Exception {
    private long id;

    public CruiseNotFoundException(long id) {
        this.id = id;
    }

    public CruiseNotFoundException(String message, long id) {
        super(message + id);
        this.id = id;
    }
}
