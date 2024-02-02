package opgave8;

import java.io.IOException;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) throws IOException {
        System.out.println("------\n8.");
        // How I use my memory storage:
        System.out.println("------\nMemory Storage:");
        // for string:
        DataStorage<String> stringMemoryStorage = new MemoryStorage<>();
        String stringMemoryIndexID = stringMemoryStorage.store("I got the data back out!");
        String stringMemoryData = stringMemoryStorage.retrieve(stringMemoryIndexID);
        System.out.println(stringMemoryData);
        // for integer:
        DataStorage<Integer> integerMemoryStorage = new MemoryStorage<>();
        String integerMemoryID = integerMemoryStorage.store(42);
        int data = integerMemoryStorage.retrieve(integerMemoryID);
        System.out.println("We got our int back out: " +data);

        // how I use my file storage:
        System.out.println("-------\nFilestorage:");
        DataStorage<Integer> integerFileStorage = new FileStorage<>();
        String integerKey = integerFileStorage.store(555);
        // get the data in string format:
        String retrivedIntData = String.valueOf(integerFileStorage.retrieve(integerKey));
        // tries to get the data as the intended data type and prints it:
        try {
            int intData = Integer.parseInt(retrivedIntData);
            System.out.println("Got the data successfully: " + intData);
        }catch (NumberFormatException e){
            System.out.println("Was unable to convert the data to integer");
        }
        integerFileStorage.delete(integerKey);
        // same as above but as with strings:
        DataStorage<String> stringFileStorage = new FileStorage<>();
        String stringKey = stringFileStorage.store("Wohoo! at least something is working!");
        String stringData = stringFileStorage.retrieve(stringKey);
        System.out.println(stringData);
        stringFileStorage.delete(stringKey);
        //// I am unsure if my above solution is correct, have included my first attempt below: well at least an attempt was made.
        DataStorage<Double> doubleFileStorage = new FileStorage<>(Double.class);
        String doubleKey = doubleFileStorage.store(432.022);
        String doubleKey2 = doubleFileStorage.store(4.02);
        String doubleKey3 = doubleFileStorage.store(42.022);
        double doubleData = doubleFileStorage.retrieveTest(doubleKey);
        System.out.println("info gotten successfully: " + doubleData);
        doubleFileStorage.delete(doubleKey);

        // did not setup a database for database storage, you would not have access to it anyway.
    }
}
