package threadExcercise.DTOs;

public class DadJokeDTO implements DTOInterface {
    String joke;

    @Override
    public Object getResults() {
        return joke;
    }

    @Override
    public String toString() {
        return joke;
    }
}
