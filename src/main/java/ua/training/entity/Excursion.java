package ua.training.entity;

import java.util.Objects;

public class Excursion {
    private long id;
    private String excursionName;
    private int duration;
    private long price;
    private Port port;

    public Excursion() {
    }

    public Excursion(Builder builder) {
        this.id = builder.id;
        this.excursionName = builder.excursionName;
        this.duration = builder.duration;
        this.price = builder.price;
        this.port = builder.port;
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

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Excursion excursion = (Excursion) o;
        return id == excursion.id &&
                duration == excursion.duration &&
                price == excursion.price &&
                excursionName.equals(excursion.excursionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, excursionName, duration, price);
    }

    public static class Builder {
        private long id;
        private String excursionName;
        private int duration;
        private long price;
        private Port port;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder excursionName(String excursionName) {
            this.excursionName = excursionName;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder port(Port port) {
            this.port = port;
            return this;
        }

        public Excursion build() {
            return new Excursion(this);
        }
    }

}
