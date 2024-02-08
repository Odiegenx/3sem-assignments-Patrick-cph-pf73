package threadExcercise.DTOs;

public class PokemonDTO {
    String name;
    String url;

    @Override
    public String toString() {
        return "name='" + name +
                "url='" + url +
                '}' + "\n";
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
