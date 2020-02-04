package ua.training.model.exception;

public class TicketNotFound extends Exception {
    public TicketNotFound(String message, long ticketId) {
        super(message + ticketId);
    }
}
