package opgave8;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileStorage<T> implements DataStorage<T> {
    private String path = Paths.get("").toAbsolutePath() + "/filestorage.csv"; //"C:\\Users\\Bruger\\OneDrive\\Skrivebord\\Datamatiker\\3. sem\\afleveringer\\filestorage.csv";
    private File file = new File(path);
    // storing the data stype, had to make this work around to get my retrive function to work
    private Class<T> dataType;

    // making the constructor set the data type
    public FileStorage(Class<T> dataType) {
        this.dataType = dataType;
    }

    @Override
    public String store(T data) {
        FileWriter writer = null;
        try {
            //// where the information will the saved.
            writer = new FileWriter(path);
            writer.write(data.toString());
            //// always remember to close the writer.
            writer.close();
            System.out.println("DataStore was stored succesfully");
            return "filestorage.csv";
        } catch (IOException e) {
            System.out.println("something went wrong with the filewriter");
        }
        return null;
    }
    @Override
    public T retrieve(String source) {
        // getting the file we are looking for:
        file = new File(Paths.get("").toAbsolutePath() + "/"+source);
        T data = null;
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // converts to the right datatype.
                data = converToType(line.trim());
            }
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        }catch (NullPointerException e){
            System.out.println("Could not handle the datatype");
        }
        return data;
    }
    private T converToType(String value){
        if(dataType == Integer.class){
            return (T) Integer.valueOf(value);
        }else if(dataType == String.class){
            return (T) value;
        } else if(dataType == Double.class){
            return (T) Double.valueOf(value);
        }else {
            return null;
        }
    }
}
