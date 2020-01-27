package ua.training.model.entity;

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
        private String description_eng;
        private String description_ru;

    public Cruise(Long id, String cruiseName, Ship ship,
                  List<Port> ports, List<Ticket> tickets,
                  LocalDate departureDate, LocalDate arrivalDate,
                  String description_eng, String description_ru) {
        this.id = id;
        this.cruiseName = cruiseName;
        this.ship = ship;
        this.ports = ports;
        this.tickets = tickets;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.description_eng = description_eng;
        this.description_ru = description_ru;
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

    public String getDescription_eng() {
        return description_eng;
    }

    public void setDescription_eng(String description_eng) {
        this.description_eng = description_eng;
    }

    public String getDescription_ru() {
        return description_ru;
    }

    public void setDescription_ru(String description_ru) {
        this.description_ru = description_ru;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private String cruiseName;
        private Ship ship;
        private List<Port> ports;
        private List<Ticket> tickets;
        private LocalDate departureDate;
        private LocalDate arrivalDate;
        private String descriptionEng;
        private String descriptionRu;

        public Builder id(long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.cruiseName = name;
            return this;
        }
        public Builder descriptionEng(String descriptionEng){
            this.descriptionEng = descriptionEng;
            return this;
        }

        public Builder descriptionRu(String descriptionRu){
            this.descriptionRu = descriptionRu;
            return this;
        }
        public Builder ship(Ship ship){
            this.ship = ship;
            return this;
        }
        public Builder arrivalDate(LocalDate arrivalDate){
            this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder departureDate(LocalDate departureDate){
            this.departureDate = departureDate;
            return this;
        }

        public Builder ports(List<Port> ports){
            this.ports = ports;
            return this;
        }

        public Builder tickets(List<Ticket> tickets){
            this.tickets = tickets;
            return this;
        }

        public Cruise build(){
            return  new Cruise(id,cruiseName,ship,ports,tickets,
                    departureDate,arrivalDate,descriptionEng,descriptionRu);
        }
    }
}
