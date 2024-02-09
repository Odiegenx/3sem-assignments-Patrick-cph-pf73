package threadExcercise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import threadExcercise.DTOs.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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
        String url8 = "http://numbersapi.com/random/trivia?json";
        String url9 = "https://catfact.ninja/fact";
        String url10 = "https://www.boredapi.com/api/activity";
        Map<String,Class> urlMap = new HashMap<>();
        urlMap.put(url1,PokemonResultsDTO.class);
        urlMap.put(url2, DadJokeDTO.class);
        urlMap.put(url3, ChuckNorrisJokeDTO.class);
        urlMap.put(url4, KanyeRestDTO.class);
        urlMap.put(url5, WhatTrumpThinksDTO.class);
        urlMap.put(url6, PatrickDTO.class);
        urlMap.put(url7, DogBreedDTO.class);
        urlMap.put(url8, RandomTriviaDTO.class);
        urlMap.put(url9, CatFactDTO.class);
        urlMap.put(url10, GetActivityDTO.class);
            // ----------------------------------------
        ExecutorService myService = Executors.newCachedThreadPool();
        //ExecutorService myService = Executors.newFixedThreadPool(4);
        ArrayList<Future<DTOInterface>> futures = new ArrayList<>();
        for (Map.Entry<String, Class> entry : urlMap.entrySet()) {
            String url = entry.getKey();
            Class dtoClass = entry.getValue();
            futures.add(myService.submit(() -> {
                return DTOController.getResponse1(url, dtoClass);
            }));
        }ArrayList<DTOInterface> results = new ArrayList<>();
        for (Future<DTOInterface> future: futures) {
            DTOInterface tmpFuture = future.get();
            results.add(tmpFuture);
            System.out.println(tmpFuture.getResults());
            System.out.println("--------------------------------------------");
        }

        MegaDTO megaDTO = DTOController.createMegaDTO(futures);
        //MegaDTO megaDTO1 = DTOController.createMegaDTO1(results);
        System.out.println(megaDTO);
        //System.out.println(megaDTO1);
        myService.shutdown();
        try {
           boolean isdone = myService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
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
    */
}
