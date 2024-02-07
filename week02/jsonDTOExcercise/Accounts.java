package jsonDTOExcercise;

import java.util.ArrayList;

public class Accounts
{
    ArrayList<Account> accounts = new ArrayList<>();

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                 accounts +
                '}'+"\n --------------";
    }
}
