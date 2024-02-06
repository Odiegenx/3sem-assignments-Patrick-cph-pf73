package dataDTOExcercise.movieController;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MovieFun {
        private final Gson gson = new Gson();
        private final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS) // Adjust timeout as needed
                .build();

        public MovieSearchResultsDTO getAllMoviesWithHigherRatingThan(double rating) {
            int maxRetries = 3; // Maximum number of retries
            int retryDelaySeconds = 1; // Initial delay before the first retry in seconds
            int totalRetries = 0;
            ArrayList<MovieSearchDTO> allResults = new ArrayList<>();
            int totalpages = 1;

            while (totalRetries <= maxRetries) {
                for (int currentPage = 1; currentPage <= totalpages; currentPage++) {
                    String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page={currentPage}&sort_by=vote_average.asc&vote_average.gte={rating}"
                            .replace("{rating}",String.valueOf(rating))
                            .replace("{currentPage}",String.valueOf(currentPage));
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .addHeader("accept", "application/json")
                            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string();
                        if (response.isSuccessful()) {
                            if (currentPage <= 1) {
                                MoviePageInfoDTO pageInfo = gson.fromJson(responseBody, MoviePageInfoDTO.class);
                                totalpages = pageInfo.total_pages;
                            }
                            MovieSearchResultsDTO movieResultsDTO = gson.fromJson(responseBody, MovieSearchResultsDTO.class);
                            allResults.addAll(movieResultsDTO.getResults());
                        } else {
                            // Retry if response is not successful
                            if (totalRetries < maxRetries) {
                                totalRetries++;
                                System.out.println("Retry attempt " + totalRetries + " after " + retryDelaySeconds + " seconds");
                                Thread.sleep(retryDelaySeconds * 1000);
                                retryDelaySeconds *= 2; // Exponential backoff
                                continue; // Retry the same request
                            } else {
                                throw new RuntimeException("Failed to fetch data after " + maxRetries + " retries");
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        // Handle exceptions
                        throw new RuntimeException(e);
                    }
                }
                break; // Break the retry loop if successful
            }

            MovieSearchResultsDTO totalResults = new MovieSearchResultsDTO();
            totalResults.setResults(allResults);
            return totalResults;
        }
    }


