package dataDTOExcercise;

import java.util.Arrays;

public class MovieResultsDTO {
    MovieDTO[] movie_results;
    public MovieResultsDTO(MovieDTO[] movie_results) {
        this.movie_results = movie_results;
    }
    public MovieDTO[] getMovie_results() {
        return movie_results;
    }
    public MovieDTO getMovieDTO() {
        return movie_results[0];
    }

    @Override
    public String toString() {
        return "MovieResultsDTO{" +
                "movie_results=" + Arrays.toString(movie_results) +
                '}';
    }
}
