package opgave8;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class FileStorage<T extends Serializable > implements DataStorage<T> {
    private String path = "filestorage.csv"; //"C:\\Users\\Bruger\\OneDrive\\Skrivebord\\Datamatiker\\3. sem\\afleveringer\\filestorage.csv";
    private File file = new File(path);
    // storing the data type, had to make this work around to get my retrive function to work
    private Class<T> dataType;

    // making the constructor set the data type
    public FileStorage() {
    }
    public FileStorage(Class<T> dataType ){
        this.dataType = dataType;
    }

    @Override
    public String store(T data) {
        String key = UUID.randomUUID().toString();// generates a unid key
        String dataAsString = data.toString(); // gets the data as a string.
        String strToFile = key+","+dataAsString;
        try (FileWriter writer = new FileWriter(path,true)) {
            writer.write(strToFile+System.lineSeparator()); // writes to the file
            return key;
        } catch (IOException e) {
            return "Something went wrong with the file writer";
        }
    }
    @Override
    public T retrieve(String source) {
        T data = null;
        try {
            Scanner scan = new Scanner(file);
            String keyToFind = source+",";
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if(line.contains(keyToFind)){
                    String[] aarray = line.split(",");
                    if(aarray.length >= 1){
                        String valueAsString = aarray[1].trim();
                        data = (T) valueAsString;
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        }catch (NullPointerException e){
            System.out.println("Could not handle the datatype");
        }
        return data;
    }
    public T retrieveTest(String source) {
        T data = null;
        try {
            Scanner scan = new Scanner(file);
            String keyToFind = source+",";
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if(line.contains(keyToFind)){
                    String[] aarray = line.split(",");
                    if(aarray.length >= 1){
                        String valueAsString = aarray[1].trim();
                        data = (T) convertToType(valueAsString.trim());
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        }catch (NullPointerException e){
            System.out.println("Could not handle the datatype");
        }
        return data;
    }
    @Override
    public void delete(String key) {
            try {
                try (Scanner scanner = new Scanner(file)) {
                    String lineToRemove = key + ",";
                    Map<String, String> tmpData = new HashMap<>();
                    int count = 0;
                    String deletedValue = null;
                    while (scanner.hasNextLine()) {
                        String currentLine = scanner.nextLine();
                        String[] tmpStringArray = currentLine.split(",");
                        if(currentLine.startsWith(lineToRemove)) {
                            deletedValue = tmpStringArray[1];
                        }else{
                            tmpData.put(tmpStringArray[0], tmpStringArray[1]);
                        }
                        count++;
                    }
                    if(!tmpData.isEmpty() || count>=1) {
                        Writer writer = new FileWriter(file);
                        for (Map.Entry<String, String> entry : tmpData.entrySet()) {
                            writer.write(entry.getKey() + "," + entry.getValue() + System.lineSeparator());
                        }
                        System.out.println("Delete function was successful, "+deletedValue+" got removed.");
                        writer.close();
                        return;
                    }
                    System.out.println("there was nothing to delete");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Something went wrong with the delete operation: " + e.getMessage());
            }
        }
    private T convertToType(String value) {
        if (dataType == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (dataType == String.class) {
            return (T) value;
        } else if (dataType == Double.class) {
            return (T) Double.valueOf(value);
        } else {
            return null;
        }
    }
}
