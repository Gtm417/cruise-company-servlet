package ua.training.exception;

public class UserNotFoundException extends Exception {
    private String login;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, String login) {
        super(message + login);
        this.login = login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
