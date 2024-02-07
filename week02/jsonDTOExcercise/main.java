package jsonDTOExcercise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataDTOExcercise.movieController.MovieSearchResultsDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        // part 1:

        // part 2: look in the account.json file

        // part 3:
        Accounts accounts;
        File file = new File("week02/jsonDTOExcercise/account.json");
        try {
            accounts = getAccounts(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 2:
        //accounts.getAccounts().forEach(System.out::println);
        // :

        ArrayList<AccountDTO> accountDTOS = getArrayOfAccountDTOs(accounts.getAccounts());
        print(accountDTOS);
    }
    public static Accounts getAccounts(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String json = "";
        while(scan.hasNextLine()){
            json += scan.nextLine();
        }
        Accounts accounts = gson.fromJson(json, Accounts.class);
        return accounts;
    }
    public static AccountDTO getAccountDTO(Account account){
        String fullName = account.firstName+" "+account.lastName;
        String city = account.getAddress().getCity();
        String zipCode = String.valueOf(account.getAddress().getZipCode());
        String isActive = String.valueOf(account.getAccount().isActive());
        AccountDTO tmpAccountDTO = new AccountDTO(fullName,city,zipCode,isActive);
        return tmpAccountDTO;
    }
    public static ArrayList<AccountDTO> getArrayOfAccountDTOs(ArrayList<Account> accounts){
        ArrayList<AccountDTO> tmpAccounts = new ArrayList<>();
        for (Account account:accounts) {
            tmpAccounts.add(getAccountDTO(account));
        }
      return tmpAccounts;
    }
    public static void print(ArrayList<?> account ){
        for(Object account1: account){
            System.out.println(account1.toString());
        }
    }
}
