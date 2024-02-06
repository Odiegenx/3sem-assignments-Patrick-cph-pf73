package dataDTOExcercise.movieController;
import java.util.ArrayList;

public class MovieSearchResultsDTO extends Media {
    ArrayList<MovieSearchDTO> results;

    public ArrayList<MovieSearchDTO> getResults() {
        return results;
    }
    public void setResults(ArrayList<MovieSearchDTO> allResults) {
        this.results = allResults;
    }

    @Override
    public String toString() {
        return "results=" + super.toString() + results +
                '}';
    }
}
