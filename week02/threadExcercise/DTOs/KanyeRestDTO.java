package threadExcercise.DTOs;

public class KanyeRestDTO implements DTOInterface{
    String quote;
    @Override
    public Object getResults() {
        return quote;
    }

    @Override
    public String toString() {
        return quote;
    }
}
