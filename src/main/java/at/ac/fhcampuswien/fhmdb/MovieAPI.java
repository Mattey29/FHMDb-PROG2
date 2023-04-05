package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {
    // Live URL
    // private static final String BASE_URL = " https://prog2.fh-campuswien.ac.at";

    //Work Around API
    private static final String BASE_URL = "http://localhost:8080";

    private OkHttpClient client;
    private Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> getMovies() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+"/movies")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        Movie[] moviesArray = gson.fromJson(responseBody, Movie[].class);
        List<Movie> movies = Arrays.asList(moviesArray);

        return movies;
    }
}
