package ua.training.entity;

import java.util.List;

public class Port {
    private long id;
    private String portName;
    private List<Excursion> excursions;
    private List<Cruise> cruises;

    public Port(Builder builder) {
        this.id = builder.id;
        this.portName = builder.portName;
        this.excursions = builder.excursions;
        this.cruises = builder.cruises;
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

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }

    public List<Cruise> getCruises() {
        return cruises;
    }

    public void setCruises(List<Cruise> cruises) {
        this.cruises = cruises;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", portName='" + portName + '\'' +
                '}';
    }

    public static class Builder {
        private long id;
        private String portName;
        private List<Excursion> excursions;
        private List<Cruise> cruises;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder portName(String portName) {
            this.portName = portName;
            return this;
        }

        public Builder excursions(List<Excursion> excursions) {
            this.excursions = excursions;
            return this;
        }

        public Builder cruises(List<Cruise> cruises) {
            this.cruises = cruises;
            return this;
        }

        public Port build() {
            return new Port(this);
        }
    }
}
