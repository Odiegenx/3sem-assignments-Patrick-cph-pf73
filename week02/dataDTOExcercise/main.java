package dataDTOExcercise;

import dataDTOExcercise.movieController.MovieController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDate;

public class main {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        System.out.println(getResponce());
        //task: 1 Get movie by movie ID:
        String responceBody1 = getResponceByMovieID("tt5177120");
        System.out.println(getMovieDTO(responceBody1));
        //System.out.println(getMovieDTO(getResponceByMovieID("r3r235")));
        System.out.println(MovieController.getMovesByRating(8.5));
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
