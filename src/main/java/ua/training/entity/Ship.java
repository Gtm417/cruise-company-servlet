package ua.training.entity;

public class Ship {
    private Long id;
    private String shipName;
    private int currentPassengerAmount;
    private int maxPassengerAmount;
    private Cruise cruise;

    public Ship(Builder builder) {
        this.id = builder.id;
        this.shipName = builder.shipName;
        this.currentPassengerAmount = builder.currentPassengerAmount;
        this.maxPassengerAmount = builder.maxPassengerAmount;
        this.cruise = builder.cruise;
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

    public int getCurrentPassengerAmount() {
        return currentPassengerAmount;
    }

    public int getMaxPassengerAmount() {
        return maxPassengerAmount;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", shipName='" + shipName + '\'' +
                ", currentPassengerAmount=" + currentPassengerAmount +
                ", maxPassengerAmount=" + maxPassengerAmount +
                '}';
    }

    public static class Builder {
        private Long id;
        private String shipName;
        private int currentPassengerAmount;
        private int maxPassengerAmount;
        private Cruise cruise;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.shipName = name;
            return this;
        }

        public Builder currentPassengerAmount(int currentPassengerAmount) {
            this.currentPassengerAmount = currentPassengerAmount;
            return this;
        }

        public Builder maxPassengerAmount(int maxPassengerAmount) {
            this.maxPassengerAmount = maxPassengerAmount;
            return this;
        }

        public Builder cruise(Cruise cruise) {
            this.cruise = cruise;
            return this;
        }

        public Ship build() {
            return new Ship(this);
        }

    }
}
