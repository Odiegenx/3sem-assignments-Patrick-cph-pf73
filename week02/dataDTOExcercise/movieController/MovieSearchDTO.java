package dataDTOExcercise.movieController;

public class MovieSearchDTO{

    String title;
    String overview;
    String release_date;
    double vote_average;

    @Override
    public String toString() {
        return "\n"+ "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release date: " + release_date +
                ", vote_average=" + vote_average +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }
}
