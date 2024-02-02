package opgave6;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) {
        //// 2.
        List<Book> bookList = new ArrayList<>();
        //// my books!
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 180, 4.2));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 281, 4.5));
        bookList.add(new Book("1984", "George Orwell", 1949, 328, 4.0));
        bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 224, 3.9));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 1813, 279, 4.7));
        bookList.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937, 310, 4.6));
        bookList.add(new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", 1967, 417, 4.4));
        bookList.add(new Book("The Da Vinci Code", "Dan Brown", 2003, 454, 3.8));
        bookList.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, 1178, 4.8));
        bookList.add(new Book("The Lord of the Rings 2", "J.R.R. Tolkien", 1954, 1178, 2.8));
        bookList.add(new Book("The Lord of the Rings 3", "J.R.R. Tolkien", 1954, 1178, 4.2));
        bookList.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, 309, 4.9));
        ////
        //// 3.
        System.out.println("-------\n3.");
        //// Average rating :
        double sum = bookList.stream().map(Book::getRating)
                .reduce((x,y) -> Double.sum(x,y))
                .get();
        // double sum1 = bookList.stream().mapToDouble(x -> x.getRating()).sum(); // just tested to see if it worked.
        double averageRating = sum / bookList.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        System.out.println("The average rating of the books are: " + decimalFormat.format(averageRating));
        /////////////////
        // Imperative approach
        double testSum = 0;
        for (Book b: bookList) {
            testSum += b.rating;
        }
        double testAverageRating = testSum / bookList.size();
        System.out.println("(Imperative) The test rating: "+ testAverageRating);
        ///
        //Declarative approach:
        OptionalDouble averageRatingTest = bookList.stream()
                .mapToDouble(Book::getRating)
                .average();
        System.out.println("(Declarative) The test rating: " + averageRatingTest.getAsDouble());
        ////////////////









        System.out.println("------");
        //// Finding books published after a certain year:
        int yearToCheck = 1950;
        Predicate<Book> publishedYearChecker = x -> x.getPublicationYear() > yearToCheck;
        List<Book> filteredBooks = bookList.stream()
                .filter(publishedYearChecker)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Books published after: " + yearToCheck);
        filteredBooks.forEach(System.out::println);
        System.out.println("-------");
        //// sorting books by rating:
        System.out.println("Books sorted by rating descending: ");
        Function<Book,Double> getRating = book -> book.getRating();
        List<Book> sortedBooksDesc = bookList.stream()
                .sorted(Comparator.comparing(getRating))  // tried to add a functional interface
                .collect(Collectors.toCollection(ArrayList::new));
        //// printing books:
        sortedBooksDesc.forEach(System.out::println);
        System.out.println("Books sorted by rating ascending: ");
        bookList.stream()
                .sorted(Comparator.comparing(Book::getRating).reversed())
                .forEach(System.out::println);
        System.out.println("--------");
        ////
        // getting highest rated book
        System.out.println("Highest rated book is: ");
        Book bestBook = bookList.stream().max(Comparator.comparing(getRating)).get();
        System.out.println(bestBook.getTitle());
        ////
        System.out.println("-----");
        // grouping the books by author in a map and getting the average rating of their books
        Map<String, List<Book>> groupByAuthor = bookList.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor()));
        groupByAuthor.forEach((author,books) ->{
            System.out.println("Books by author: " +author);
            books.forEach(System.out::println);
            Double average = books.stream().mapToDouble(x -> x.rating).average().getAsDouble();
            if(books.size()>1) {
                System.out.println("The average rating of their books are: " + average);
            }
        });
        ////
        // Total sum of all the pages in the book list
        System.out.println("----------");
        int totalPages = bookList.stream().mapToInt(x -> x.getPages()).sum();
        System.out.println("Total amount of pages: "+totalPages);
    }
}
