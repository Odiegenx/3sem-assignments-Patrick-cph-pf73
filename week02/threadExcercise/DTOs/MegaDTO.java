package threadExcercise.DTOs;

public class MegaDTO {
        private PokemonResultsDTO pokemonResults;
        private DadJokeDTO dadJoke;
        private ChuckNorrisJokeDTO chuckNorrisJoke;
        private KanyeRestDTO kanyeRest;
        private WhatTrumpThinksDTO whatTrumpThinks;
        private PatrickDTO patrick;
        private DogBreedDTO dogBreed;
        private RandomTriviaDTO randomTrivia;
        private CatFactDTO catFact;
        private GetActivityDTO getActivity;

    public PokemonResultsDTO getPokemonResults() {
        return pokemonResults;
    }

    public void setPokemonResults(PokemonResultsDTO pokemonResults) {
        this.pokemonResults = pokemonResults;
    }

    public DadJokeDTO getDadJoke() {
        return dadJoke;
    }

    public void setDadJoke(DadJokeDTO dadJoke) {
        this.dadJoke = dadJoke;
    }

    public ChuckNorrisJokeDTO getChuckNorrisJoke() {
        return chuckNorrisJoke;
    }

    public void setChuckNorrisJoke(ChuckNorrisJokeDTO chuckNorrisJoke) {
        this.chuckNorrisJoke = chuckNorrisJoke;
    }

    public KanyeRestDTO getKanyeRest() {
        return kanyeRest;
    }

    public void setKanyeRest(KanyeRestDTO kanyeRest) {
        this.kanyeRest = kanyeRest;
    }

    public WhatTrumpThinksDTO getWhatTrumpThinks() {
        return whatTrumpThinks;
    }

    public void setWhatTrumpThinks(WhatTrumpThinksDTO whatTrumpThinks) {
        this.whatTrumpThinks = whatTrumpThinks;
    }

    public PatrickDTO getPatrick() {
        return patrick;
    }

    public void setPatrick(PatrickDTO patrick) {
        this.patrick = patrick;
    }

    public DogBreedDTO getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(DogBreedDTO dogBreed) {
        this.dogBreed = dogBreed;
    }

    public RandomTriviaDTO getRandomTrivia() {
        return randomTrivia;
    }

    public void setRandomTrivia(RandomTriviaDTO randomTrivia) {
        this.randomTrivia = randomTrivia;
    }

    public CatFactDTO getCatFact() {
        return catFact;
    }

    public void setCatFact(CatFactDTO catFact) {
        this.catFact = catFact;
    }

    public GetActivityDTO getGetActivity() {
        return getActivity;
    }

    public void setGetActivity(GetActivityDTO getActivity) {
        this.getActivity = getActivity;
    }

    @Override
    public String toString() {
        return "MegaDTO Content = \n" +
                "PokemonResults = " + pokemonResults.toString()+"\n" +
                "DadJoke = " + dadJoke.toString()+"\n"+
                "ChuckNorrisJoke = " + chuckNorrisJoke.toString()+"\n" +
                "KanyeRest = " +  kanyeRest.toString()+"\n" +
                "WhatTrumpThinks = " + whatTrumpThinks.toString()+"\n" +
                "Patrick = " + patrick.toString()+"\n" +
                "DogBreed = " + dogBreed.toString()+"\n" +
                "RandomTrivia = " + randomTrivia.toString()+"\n" +
                "CatFact = " + catFact.toString()+"\n" +
                "GetActivity = " + getActivity.toString() +
                '}';
    }
}
