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
        /*
            What does JSON stand for?:
            JavaScript Object Notation. It's a text format for storing and transporting data

            What is the difference between JSON and XML?
            JSON is quicker to read, doesn't use end tags,
            is shorter and can use arrays!
            JSON is much simpler to parse than XML and Json is
            parsed into a ready to use JS object.
            Also, JSON is generally faster to use.

            For what is JSON generally used for?:
            For staring and transporting information, mostly between computers / web servers.

            Write down the 6 data types in JSON:
            Stings, numbers, objects(JSON object), arrays, boolean and null.
            it cannot store: a function,
                             a date,
                             and undefined.

            Write down the 4 JSON syntax rules:
            The data is stored by name/value pair(names are written in double quotes and values as well if it's a string,seperated by a colon),
            data / objects are separated by cammas,
            curly brackets holds objects,
            square brackets holds arrays.
         */

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
