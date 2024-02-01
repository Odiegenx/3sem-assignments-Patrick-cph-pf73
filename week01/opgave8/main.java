package opgave8;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("------\n8.");
        DataStorage<String> stringMemoryStorage = new MemoryStorage<>();
        stringMemoryStorage.store("I got the data back out!");
        System.out.println(stringMemoryStorage.retrieve("I got the data back out!"));
    }
}
