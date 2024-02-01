package opgave8;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("------\n8.");
        // How I use my memory storage class:
        System.out.println("------\nMemory Storage:");
        DataStorage<String> stringMemoryStorage = new MemoryStorage<>();
        String stringMemoryID = stringMemoryStorage.store("I got the data back out!");
        String stringMemoryData = stringMemoryStorage.retrieve(stringMemoryID);
        System.out.println(stringMemoryData);
        DataStorage<Integer> integerMemoryStorage = new MemoryStorage<>();
        String integerMemoryID = integerMemoryStorage.store(42);
        int data = integerMemoryStorage.retrieve(integerMemoryID);
        System.out.println("We got our int back out: " +data);

        // how I use my file storage class:
        System.out.println("-------\nFilestorage:");
        DataStorage<Integer> integerFileStorage = new FileStorage<>(Integer.class);
        String integerFileName = integerFileStorage.store(555);
        int intData = integerFileStorage.retrieve(integerFileName);
        System.out.println("Got the data succesfully: " + intData);

        DataStorage<String> stringFileStorage = new FileStorage<>(String.class);
        String stringFileName = stringFileStorage.store("Wohoo, atleast something is working!");
        String stringData = stringFileStorage.retrieve(stringFileName);
        System.out.println(stringData);
    }
}
