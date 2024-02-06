package dataDTOExcercise.interfaces;
// task 3 Adding functionality

import dataDTOExcercise.movieController.Media;

public interface mediaInterface<T extends Media> {
    T getMoviesWithHigherRatingThan(double rating);
    T getMoviesSortedByReleaseDate(int releaseYear);
}
