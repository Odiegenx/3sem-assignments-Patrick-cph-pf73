package jsonDTOExcercise;

import java.util.ArrayList;

public class Account {
    String firstName;
    String lastName;
    String birthDate;
    BankAccount account;
    Address address;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public BankAccount getAccount() {
        return account;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Account:\n"+
                "First Name='" + firstName  +
                ", Last Name='" + lastName  +
                ", Birthdate='" + birthDate  +
                "\n "+ account +
                "\n "+address +
                '}'+"\n ----------";
    }
}
