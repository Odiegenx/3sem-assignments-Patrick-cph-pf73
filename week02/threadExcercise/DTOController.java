package threadExcercise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import threadExcercise.DTOs.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DTOController{
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String url;
    //T dataType;
    DTOController(String url){
        this.url = url;
    }
    /*
    public static String getResponse(String url){
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String html = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            return html;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    */
    public static <T extends DTOInterface> T getResponse1(String url, Class<T> inputClass){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept","application/json")
                .build();

        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            T  tmp = gson.fromJson(responseBody, inputClass);
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object getDTO(String response,Class<?> inputClass){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object pageDTO = gson.fromJson(response,inputClass);
        return pageDTO;
    }
    public static PokemonResultsDTO getPokemonResultsDTO(String responsStr){
        PokemonResultsDTO tmp = gson.fromJson(responsStr, PokemonResultsDTO.class);
        return tmp;
    }
    public static void chechResponse(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept","application/json")
                .build();
        try(Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            System.out.println(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MegaDTO createMegaDTO(ArrayList<Future<DTOInterface>> futures) {
        MegaDTO tmpMegaDTO = new MegaDTO();
        for (Future<DTOInterface> future: futures) {
            DTOInterface tmpFuture = null;
            try {
                tmpFuture = future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(tmpFuture instanceof PokemonResultsDTO){
                tmpMegaDTO.setPokemonResults((PokemonResultsDTO) tmpFuture);
            } else if(tmpFuture instanceof DadJokeDTO){
                tmpMegaDTO.setDadJoke((DadJokeDTO) tmpFuture);
            } else if(tmpFuture instanceof ChuckNorrisJokeDTO){
                tmpMegaDTO.setChuckNorrisJoke((ChuckNorrisJokeDTO) tmpFuture);
            } else if(tmpFuture instanceof KanyeRestDTO){
                tmpMegaDTO.setKanyeRest((KanyeRestDTO) tmpFuture);
            } else if(tmpFuture instanceof WhatTrumpThinksDTO){
                tmpMegaDTO.setWhatTrumpThinks((WhatTrumpThinksDTO) tmpFuture);
            } else if(tmpFuture instanceof PatrickDTO){
                tmpMegaDTO.setPatrick((PatrickDTO) tmpFuture);
            } else if(tmpFuture instanceof DogBreedDTO){
                tmpMegaDTO.setDogBreed((DogBreedDTO) tmpFuture);
            } else if(tmpFuture instanceof RandomTriviaDTO){
                tmpMegaDTO.setRandomTrivia((RandomTriviaDTO) tmpFuture);
            } else if(tmpFuture instanceof CatFactDTO){
                tmpMegaDTO.setCatFact((CatFactDTO) tmpFuture);
            } else if(tmpFuture instanceof GetActivityDTO){
                tmpMegaDTO.setGetActivity((GetActivityDTO) tmpFuture);
            }
        }
        return tmpMegaDTO;
    }
    public static MegaDTO createMegaDTO1(ArrayList<DTOInterface> DTOs) {
        MegaDTO tmpMegaDTO = new MegaDTO();
        for (DTOInterface dto: DTOs) {
            if(dto instanceof PokemonResultsDTO){
                tmpMegaDTO.setPokemonResults((PokemonResultsDTO) dto);
            } else if(dto instanceof DadJokeDTO){
                tmpMegaDTO.setDadJoke((DadJokeDTO) dto);
            } else if(dto instanceof ChuckNorrisJokeDTO){
                tmpMegaDTO.setChuckNorrisJoke((ChuckNorrisJokeDTO) dto);
            } else if(dto instanceof KanyeRestDTO){
                tmpMegaDTO.setKanyeRest((KanyeRestDTO) dto);
            } else if(dto instanceof WhatTrumpThinksDTO){
                tmpMegaDTO.setWhatTrumpThinks((WhatTrumpThinksDTO) dto);
            } else if(dto instanceof PatrickDTO){
                tmpMegaDTO.setPatrick((PatrickDTO) dto);
            } else if(dto instanceof DogBreedDTO){
                tmpMegaDTO.setDogBreed((DogBreedDTO) dto);
            } else if(dto instanceof RandomTriviaDTO){
                tmpMegaDTO.setRandomTrivia((RandomTriviaDTO) dto);
            } else if(dto instanceof CatFactDTO){
                tmpMegaDTO.setCatFact((CatFactDTO) dto);
            } else if(dto instanceof GetActivityDTO){
                tmpMegaDTO.setGetActivity((GetActivityDTO) dto);
            }
        }
        return tmpMegaDTO;
    }
}
