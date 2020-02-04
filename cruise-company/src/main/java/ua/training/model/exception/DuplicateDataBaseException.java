package ua.training.model.exception;


public class DuplicateDataBaseException extends Exception {
    public DuplicateDataBaseException(Throwable cause) {
        super(cause);
    }

    public DuplicateDataBaseException(String message, Object object) {
        super(message + " " + object.toString());
    }


}
