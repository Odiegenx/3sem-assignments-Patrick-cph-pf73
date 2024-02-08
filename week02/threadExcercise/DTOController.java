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
import threadExcercise.DTOs.PokemonResultsDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class DTOController /* implements Callable*/ {
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
    public static String getResponse1(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept","application/json")
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
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

}
