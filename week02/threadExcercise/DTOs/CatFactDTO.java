package threadExcercise.DTOs;

public class CatFactDTO implements DTOInterface {
    String fact;

    @Override
    public Object getResults() {
        return fact;
    }

    @Override
    public String toString() {
        return fact;
    }
}
