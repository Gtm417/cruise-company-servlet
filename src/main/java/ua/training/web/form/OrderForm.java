package ua.training.web.form;

public class OrderForm {
    private String firstName;
    private String secondName;
    private String ticket;

    public OrderForm(String firstName, String secondName, String ticket) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.ticket = ticket;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
