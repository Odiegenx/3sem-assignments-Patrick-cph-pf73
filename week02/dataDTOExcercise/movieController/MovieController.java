package dataDTOExcercise.movieController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MovieController {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static MovieScearchResultsDTO getMovesByRating(double rating){
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.asc&vote_average.gte={rating}&vote_count.gte=50"
                .replace("{rating}",String.valueOf(rating));
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            MovieScearchResultsDTO movieResultsDTO = gson.fromJson(res,MovieScearchResultsDTO.class);
            return movieResultsDTO;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
