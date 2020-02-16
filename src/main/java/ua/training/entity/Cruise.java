package ua.training.entity;

import java.time.LocalDate;
import java.util.List;

public class Cruise {
    private Long id;
    private String cruiseName;
    private Ship ship;
    private List<Port> ports;
    private List<Ticket> tickets;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String descriptionEng;
    private String descriptionRu;

    public Cruise(Builder builder) {
        this.id = builder.id;
        this.cruiseName = builder.cruiseName;
        this.ship = builder.ship;
        this.ports = builder.ports;
        this.tickets = builder.tickets;
        this.departureDate = builder.departureDate;
        this.arrivalDate = builder.arrivalDate;
        this.descriptionEng = builder.descriptionEng;
        this.descriptionRu = builder.descriptionRu;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCruiseName() {
        return cruiseName;
    }

    public void setCruiseName(String cruiseName) {
        this.cruiseName = cruiseName;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", cruiseName='" + cruiseName + '\'' +
                ", ship=" + ship +
                ", tickets=" + tickets +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", descriptionEng='" + descriptionEng + '\'' +
                ", descriptionRu='" + descriptionRu + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String cruiseName;
        private Ship ship;
        private List<Port> ports;
        private List<Ticket> tickets;
        private LocalDate departureDate;
        private LocalDate arrivalDate;
        private String descriptionEng;
        private String descriptionRu;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.cruiseName = name;
            return this;
        }

        public Builder descriptionEng(String descriptionEng) {
            this.descriptionEng = descriptionEng;
            return this;
        }

        public Builder descriptionRu(String descriptionRu) {
            this.descriptionRu = descriptionRu;
            return this;
        }

        public Builder ship(Ship ship) {
            this.ship = ship;
            return this;
        }

        public Builder arrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder departureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder ports(List<Port> ports) {
            this.ports = ports;
            return this;
        }

        public Builder tickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Cruise build() {
            return new Cruise(this);
        }
    }
}
