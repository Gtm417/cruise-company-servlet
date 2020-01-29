package ua.training.model.dto;

public class OrderDTO {
    private long cruiseId;
    private long userId;
    private long ticketId;
    private String firstName;
    private String secondName;

    public OrderDTO(long cruiseId, long userId, long ticketId, String firstName, String secondName) {
        this.cruiseId = cruiseId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public long getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(long cruiseId) {
        this.cruiseId = cruiseId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
