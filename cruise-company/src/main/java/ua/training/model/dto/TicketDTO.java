package ua.training.model.dto;

public class TicketDTO {
    private long id;
    private String ticketName;
    private long priceWithDiscount;

    public TicketDTO(long id, String ticketName, long priceWithDiscount) {
        this.id = id;
        this.ticketName = ticketName;
        this.priceWithDiscount = priceWithDiscount;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public long getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(long priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "id=" + id +
                ", ticketName='" + ticketName + '\'' +
                ", priceWithDiscount=" + priceWithDiscount +
                '}';
    }
}
