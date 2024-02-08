package threadExcercise.DTOs;

public class RandomTriviaDTO implements DTOInterface{
    String text;
    @Override
    public Object getResults() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
