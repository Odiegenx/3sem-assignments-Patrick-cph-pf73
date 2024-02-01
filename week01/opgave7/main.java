package opgave7;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args){

        ////2
        List<Transaction> transactions = List.of(
                new Transaction(1, 100.0, "USD"),
                new Transaction(2, 150.0, "EUR"),
                new Transaction(3, 200.0, "USD"),
                new Transaction(4, 75.0, "GBP"),
                new Transaction(5, 120.0, "EUR"),
                new Transaction(6, 300.0, "GBP")
        );
        //// 3

        System.out.println("total sum :"+transactions.stream().mapToDouble(Transaction::getAmount).sum());
        Map<String, List<Transaction>> transactionsAsMapByCurrency = transactions.stream().collect(Collectors.groupingBy(x -> x.getCurrency()));
        transactionsAsMapByCurrency.forEach((currency,transactionList) -> {
            System.out.println(currency);
            System.out.println("total sum of " + currency+": " + transactionList.stream()
                    .mapToDouble(Transaction::getAmount).average().getAsDouble());
        });
         Transaction higestTransaction = transactions.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .get();
        System.out.println("The currency with the highest transaction amount is: "+ higestTransaction);
        /////
        Double averageAmount = transactions.stream().mapToDouble(x -> x.getAmount()).average().getAsDouble();
        System.out.println("The average amount is: "+averageAmount);

    }
}
