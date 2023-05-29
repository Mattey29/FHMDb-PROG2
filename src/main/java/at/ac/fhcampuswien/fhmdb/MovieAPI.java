package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exception.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MovieAPI {
    // Live URL
    private static final String BASE_URL = " https://prog2.fh-campuswien.ac.at";

    //Work Around API
    //private static final String BASE_URL = "http://localhost:8080";

    private OkHttpClient client;
    private Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> getMovies(String lookupQuery, Genre lookupGenre, Integer lookupReleaseYear, Double lookupRatingFrom) {
        List<Movie> movies = new ArrayList<>();
        try{

            String url = new MovieAPIRequestBuilder(BASE_URL + "/movies")
                    .query(lookupQuery)
                    .genre(lookupGenre)
                    .releaseYear(lookupReleaseYear)
                    .ratingFrom(lookupRatingFrom)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            Movie[] moviesArray = gson.fromJson(responseBody, Movie[].class);
            movies = Arrays.asList(moviesArray);

        } catch (IOException e){
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
        return movies;
    }


    public Movie getMovieById(String id) {
        Movie movie = new Movie();
        try {
            String url = new MovieAPIRequestBuilder(BASE_URL + "/movies/" + id)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            movie = gson.fromJson(responseBody, Movie.class);

        } catch (IOException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", e.getMessage());
        }
        return movie;
    }
}
