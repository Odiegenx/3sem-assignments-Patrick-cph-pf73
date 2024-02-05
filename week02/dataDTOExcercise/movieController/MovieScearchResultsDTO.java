package dataDTOExcercise.movieController;

import dataDTOExcercise.MovieDTO;

import java.util.Arrays;

public class MovieScearchResultsDTO {
    MovieScearchDTO[] results;

    public MovieScearchDTO[] getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MovieScearchResultsDTO{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
