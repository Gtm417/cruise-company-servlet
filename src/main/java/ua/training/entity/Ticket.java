package ua.training.entity;

import java.util.List;

public class Ticket {

    private long id;
    private String ticketName;
    private long price;
    private int discount;
    private long priceWithDiscount;
    private Cruise cruise;
    private List<Order> orders;


    public Ticket(Builder builder) {
        this.id = builder.id;
        this.ticketName = builder.ticketName;
        this.price = builder.price;
        this.discount = builder.discount;
        this.priceWithDiscount = builder.priceWithDiscount;
        this.cruise = builder.cruise;
        this.orders = builder.orders;
    }

    public static Builder builder() {
        return new Builder();
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(long priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketName='" + ticketName + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", priceWithDiscount=" + priceWithDiscount +
                '}';
    }

    public static class Builder {
        private long id;
        private String ticketName;
        private long price;
        private int discount;
        private long priceWithDiscount;
        private Cruise cruise;
        private List<Order> orders;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder ticketName(String ticketName) {
            this.ticketName = ticketName;
            return this;
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder discount(int discount) {
            this.discount = discount;
            return this;
        }

        public Builder priceWithDiscount(long priceWithDiscount) {
            this.priceWithDiscount = priceWithDiscount;
            return this;
        }

        public Builder cruise(Cruise cruise) {
            this.cruise = cruise;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }


        public Ticket build() {
            return new Ticket(this);
        }
    }
}
