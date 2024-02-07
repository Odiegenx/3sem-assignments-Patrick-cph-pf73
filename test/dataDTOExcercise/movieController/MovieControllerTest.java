package dataDTOExcercise.movieController;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    // unit tests:
    @org.junit.jupiter.api.Test
    void search() {
        MovieController movieController = new MovieController();
        ArrayList<String> movieTitles = new ArrayList<>();
        movieTitles.add("The Shawshank Redemption");
        movieTitles.add("The Godfather");
        movieTitles.add("The Dark Knight");
        movieTitles.add("The Godfather Part II");
        movieTitles.add("The Lord of the Rings: The Return of the King");
        movieTitles.add("Pulp Fiction");
        movieTitles.add("12 Angry Men");
        movieTitles.add("The Good, the Bad and the Ugly");
        movieTitles.add("Forrest Gump");
        movieTitles.add("Fight Club");
        ArrayList<MovieSearchResultsDTO> testResult = movieTitles.stream().map(movieController::search).collect(Collectors.toCollection(ArrayList::new));
        for(int i = 0;i < movieTitles.size();i++){
            assertEquals(movieTitles.get(i),testResult.get(i).getResults().get(0).getTitle());
        }
    }
}