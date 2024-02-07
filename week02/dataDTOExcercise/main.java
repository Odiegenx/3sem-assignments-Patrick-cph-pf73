package dataDTOExcercise;

import dataDTOExcercise.movieController.MovieController;
import dataDTOExcercise.movieController.MovieSearchResultsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class main {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {

        System.out.println(getResponce());
        //task: 1 Get movie by movie ID:
        String responceBody1 = getResponceByMovieID("tt5177120");
        System.out.println(getMovieDTO(responceBody1));
        //System.out.println(getMovieDTO(getResponceByMovieID("r3r235")));
        // 3. Adding functionality:
        MovieController movieController = new MovieController();
       // System.out.println(movieController.getMovieswithHigherRatingThan(8.5));
        // gettign all movies with higher than 8.5 ratings.
        double rating = 8.5;
        MovieSearchResultsDTO ratingsSearch =  movieController.getMoviesWithHigherRatingThan(rating);
        System.out.println(ratingsSearch.getResults().size());
        System.out.println(ratingsSearch);
         /*
        Turns out my request contains 1000+ pages and it seems like there's a limit for how many requests I can make on the api.
        So ended up limiting my request to 100 pages.
        */

        MovieSearchResultsDTO ratingsSearchNoLimit = movieController.getAllMoviesWithHigherRatingThanNoLimit(rating);
        System.out.println(ratingsSearchNoLimit.getResults());
        System.out.println(ratingsSearchNoLimit.getResults().size());
        int releaseYear = 2023;
        MovieSearchResultsDTO releaseYearSearch = movieController.getMoviesSortedByReleaseDate(releaseYear);
        System.out.println(releaseYearSearch);
        System.out.println(releaseYearSearch.getResults().size());

        // Movies to Find (also made a unit test(check bookmarks)):
        ArrayList<String> movieTitles = new ArrayList<>();
        movieTitles.add("The Shawshank Redemption");
        movieTitles.add("The Godfather");
        movieTitles.add("The Dark Knight");
        movieTitles.add("The Godfather: Part II");
        movieTitles.add("The Lord of the Rings: The Return of the King");
        movieTitles.add("Pulp Fiction");
        movieTitles.add("12 Angry Men");
        movieTitles.add("The Good, the Bad and the Ugly");
        movieTitles.add("Forrest Gump");
        movieTitles.add("Fight Club");
        // in case it finds more than 1 result it prints the first one.
        movieTitles.stream().map(movieController::search).map(MovieSearchResultsDTO::getResults).map(x -> x.get(0)).forEach(System.out::println);
    }
    public static String getResponce() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return res;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static String getResponceByMovieID(String movieID) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/find/{movieID}?external_source=imdb_id"
                .replace("{movieID}",movieID);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return res;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static MovieDTO getMovieDTO(String responceBody){
        MovieResultsDTO movieResultsDTO = gson.fromJson(responceBody,MovieResultsDTO.class);
        if(movieResultsDTO.getMovie_results().length > 0) {
            String date = movieResultsDTO.getMovieDTO().getRelease_date();
            String[] dateArray = date.split("-");
            MovieDTO movieDTO = movieResultsDTO.getMovieDTO();
            movieDTO.setRelease_year(dateArray[0]);
            movieDTO.setLocalDate(LocalDate.parse(date));
            return movieDTO;
        }else {
            return null;
        }
    }
}
