package threadExcercise.DTOs;

import java.util.ArrayList;

public class PokemonResultsDTO implements DTOInterface {
    ArrayList<PokemonDTO> results;

    @Override
    public String toString() {
        return "PokemonResultsDTO{" +
                "results=" + results +
                '}';
    }
    @Override
    public ArrayList<PokemonDTO> getResults() {
        return results;
    }
}
