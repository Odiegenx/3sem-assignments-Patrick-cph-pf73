package jsonDTOExcercise;

public class BankAccount {
    String id;
    String balance;
    boolean isActive;

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "BankAccount{\n" +
                "id='" + id + '\'' +
                ", balance='" + balance + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
