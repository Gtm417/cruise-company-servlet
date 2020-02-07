package ua.training.exception;

public class NotEnoughMoney extends Exception {
    private long balance;

    public NotEnoughMoney(String message, long balance) {
        super(message);
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }
}
