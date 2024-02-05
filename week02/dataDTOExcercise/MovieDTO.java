package dataDTOExcercise;

import java.time.LocalDate;

public class MovieDTO {
    String title;
    String overview;
    transient LocalDate localDate;
    String release_date;
    String release_year;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", localDate=" + localDate +
                ", release_year='" + release_year + '\'' +
                '}';
    }
}
