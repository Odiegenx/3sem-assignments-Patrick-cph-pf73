package dataDTOExcercise.movieController;

public class MovieScearchDTO {

    String title;
    String overview;
    double vote_average;

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average=" + vote_average +
                '}'+"\n";
    }
}
