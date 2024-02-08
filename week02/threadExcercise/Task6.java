package threadExcercise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataDTOExcercise.movieController.MoviePageInfoDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import threadExcercise.DTOs.PokemonResultsDTO;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.function.Function;

public class Task6 {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String url1 = "https://pokeapi.co/api/v2/pokemon/";
        String url2 = "https://icanhazdadjoke.com/";
        String url3 = "https://api.chucknorris.io/jokes/random";
        String url4 = "https://api.kanye.rest/";
        String url5 = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";
        String url6 = "https://api.agify.io/?name=Patrick";
        String url7 = "https://dog.ceo/api/breeds/image/random";
        String url8 = "https://restcountries.com/v3.1/name/India?fullText=true";
        String url9 = "https://catfact.ninja/fact";
        String url10 = "https://www.boredapi.com/api/activity";
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add(url1);
        //urlList.add(url2);
        urlList.add(url3);
        urlList.add(url4);
        urlList.add(url5);
        urlList.add(url6);
        urlList.add(url7);
        urlList.add(url8);
        urlList.add(url9);
        urlList.add(url10);
        String connection1 = DTOController.getResponse1("https://pokeapi.co/api/v2/pokemon/");
        //System.out.println(connection1);
        Object blah = DTOController.getDTO(connection1, PokemonResultsDTO.class);
        //System.out.println(blah);
        /*
        ExecutorService myService = Executors.newCachedThreadPool();
        for(int i = 0; i < urlList.size();i++) {
            myService.submit(() -> {
                //String connection11 = getResponse(urlList.get(i));
                System.out.println(connection11);
            });
        }
        */
            String blag = DTOController.getResponse1(url1);
            DTOController.getPokemonResultsDTO(blag);
        //DTOController.getResponse1(url2).toString();
            // ----------------------------------------
        ExecutorService myService = Executors.newCachedThreadPool();
        Future<PokemonResultsDTO> future1 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future2 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url2), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future3 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future4 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future5 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future6 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future7 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future8 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future9 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        Future<PokemonResultsDTO> future10 = myService.submit(() -> {
            return gson.fromJson(DTOController.getResponse1(url1), PokemonResultsDTO.class);
        });
        future1.get().toString();
        myService.shutdown();
        myService.awaitTermination(1500,TimeUnit.MICROSECONDS);


    }
}
