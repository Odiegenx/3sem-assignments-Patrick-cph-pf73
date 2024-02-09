package dataDTOExcercise.movieController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataDTOExcercise.interfaces.mediaInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class MovieController implements mediaInterface<MovieSearchResultsDTO> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    OkHttpClient client = new OkHttpClient();
    /*
    // My first attempt, realised that we got quite a few more results than what was on page 1.and made a new "improved" function.
    public MovieScearchResultsDTO getMovieswithHigherRatingThan(double rating){
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
*/
    public MovieSearchResultsDTO getMediaWithHigherRatingThan(double rating) {
        // the new "improved" version:
        ArrayList<MovieSearchDTO> allResults = new ArrayList<>();
        int totalpages = 1;
        // getting the first 3 pages of movies with higher rating than "rating", i put a limit on the votes needed to 50.
        for (int currentPage = 1; currentPage <= totalpages; currentPage++) {
            String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page={currentPage}&sort_by=vote_average.asc&vote_average.gte={rating}&vote_count.gte=50"
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
                MovieSearchResultsDTO movieResultsDTO = gson.fromJson(responseBody, MovieSearchResultsDTO.class);
                // if it's the first page of the response, we find the total amount of pages from the response.
                if(currentPage <=1){
                    MoviePageInfoDTO pageInfo = gson.fromJson(responseBody,MoviePageInfoDTO.class);
                    totalpages = pageInfo.total_pages;
                }
                allResults.addAll(movieResultsDTO.getResults());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MovieSearchResultsDTO totalResults = new MovieSearchResultsDTO();
        totalResults.setResults(allResults);
        return totalResults;
    }
    public MovieSearchResultsDTO getAllMoviesWithHigherRatingThanNoLimit(double rating) {
        // the new "improved" version:
        ArrayList<MovieSearchDTO> allResults = new ArrayList<>();
        int totalpages = 1;
        // getting the first 3 pages of movies with higher rating than "rating".
        for (int currentPage = 1; currentPage <= totalpages; currentPage++) {
            String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page={currentPage}&sort_by=vote_average.desc&vote_average.gte={rating}"
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
                // if it's the first currentPage of the response, we find the total amount of pages from the response.
                if(currentPage <=1){
                    MoviePageInfoDTO pageInfo = gson.fromJson(responseBody,MoviePageInfoDTO.class);
                    System.out.println("Total pages in ratings response: "+pageInfo.total_pages);
                    totalpages = pageInfo.total_pages;
                    if(totalpages > 1){
                        totalpages = 1;
                    }
                }
                MovieSearchResultsDTO movieResultsDTO = gson.fromJson(responseBody, MovieSearchResultsDTO.class);
                allResults.addAll(movieResultsDTO.getResults());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MovieSearchResultsDTO totalResults = new MovieSearchResultsDTO();
        ArrayList<MovieSearchDTO> resultWeWant = new ArrayList<>();
        resultWeWant.add(allResults.get(0));
        totalResults.setResults(resultWeWant);
        return totalResults;
    }


    @Override
    public MovieSearchResultsDTO getMediaSortedByReleaseDate(int releaseYear) {
        ArrayList<MovieSearchDTO> allResults = new ArrayList<>();
        int totalpages = 1;
        for (int currentPage = 1; currentPage <= totalpages; currentPage++) {
            OkHttpClient client = new OkHttpClient();
            String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page={page}&primary_release_year={releaseYear}&sort_by=primary_release_date.desc"
                    .replace("{page}",String.valueOf(currentPage))
                    .replace("{releaseYear}",String.valueOf(releaseYear));
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                // if it's the first currentPage of the response, we find the total amount of pages from the response.
                if(currentPage <=1){
                    MoviePageInfoDTO pageInfo = gson.fromJson(responseBody,MoviePageInfoDTO.class);
                    System.out.println("Total pages in release year response: "+pageInfo.total_pages);
                    totalpages = pageInfo.total_pages;
                    if(totalpages > 100){
                        totalpages = 100;
                    }
                }
                MovieSearchResultsDTO movieResultsDTO = gson.fromJson(responseBody, MovieSearchResultsDTO.class);
                allResults.addAll(movieResultsDTO.getResults());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MovieSearchResultsDTO totalResults = new MovieSearchResultsDTO();
        totalResults.setResults(allResults);
        return totalResults;
    }
    public MovieSearchResultsDTO search(String movieName){
        String tmpSearchInput = movieName.replace(" ","%20");
        ArrayList<MovieSearchDTO> allResults = new ArrayList<>();
        int totalpages = 1;
        for (int currentPage = 1; currentPage <= totalpages; currentPage++) {
            OkHttpClient client = new OkHttpClient();
            String url = "https://api.themoviedb.org/3/search/movie?query={searchInput}&include_adult=false&language=en-US&page={page}"
                    .replace("{page}",String.valueOf(currentPage))
                    .replace("{searchInput}",tmpSearchInput);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTQ2MGVkYzlkYjMyNDYzYjFjZjg2YzRhNTE2NDgxOCIsInN1YiI6IjY1YzBjYzhjYmYwOWQxMDE4NGE3OGFlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7oXOM3iuoSy0yjc_oTiE4hGpR74_1PmAZu0G57QRrKo")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                // if it's the first currentPage of the response, we find the total amount of pages from the response.
                if(currentPage <=1){
                    MoviePageInfoDTO pageInfo = gson.fromJson(responseBody,MoviePageInfoDTO.class);
                    totalpages = pageInfo.total_pages;
                    if(totalpages > 100){
                        totalpages = 100;
                    }
                }
                MovieSearchResultsDTO movieResultsDTO = gson.fromJson(responseBody, MovieSearchResultsDTO.class);
                allResults.addAll(movieResultsDTO.getResults());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MovieSearchResultsDTO totalResults = new MovieSearchResultsDTO();
        totalResults.setResults(allResults);
        return totalResults;
    }
}
