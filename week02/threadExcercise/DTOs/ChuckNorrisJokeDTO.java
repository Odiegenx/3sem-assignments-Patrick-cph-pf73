package threadExcercise.DTOs;

public class ChuckNorrisJokeDTO implements DTOInterface {
    String value;
    @Override
    public Object getResults() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
