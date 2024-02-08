package threadExcercise.DTOs;

public class DogBreedDTO implements DTOInterface{
    String message;
    @Override
    public Object getResults() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
