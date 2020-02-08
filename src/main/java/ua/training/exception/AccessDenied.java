package ua.training.exception;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }
}
