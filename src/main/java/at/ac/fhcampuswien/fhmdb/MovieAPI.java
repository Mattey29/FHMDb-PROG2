package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.HttpUrl;
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

    public List<Movie> getMovies(String lookupQuery, Genre lookupGenre, Integer lookupReleaseYear, Double lookupRatingFrom) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/movies").newBuilder();

        if (lookupQuery != null && !lookupQuery.isEmpty()) {
            urlBuilder.addQueryParameter("query", lookupQuery);
        }

        if (lookupGenre != null) {
            urlBuilder.addQueryParameter("genre", lookupGenre.name());
        }

        if (lookupReleaseYear != null) {
            urlBuilder.addQueryParameter("releaseYear", lookupReleaseYear.toString());
        }

        if (lookupRatingFrom != null) {
            urlBuilder.addQueryParameter("ratingFrom", lookupRatingFrom.toString());
        }

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        Movie[] moviesArray = gson.fromJson(responseBody, Movie[].class);
        List<Movie> movies = Arrays.asList(moviesArray);

        return movies;
    }


    public Movie getMovieById(String id) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/movies/" + id)
                .header("User-Agent", "http.agent")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        Movie movie = gson.fromJson(responseBody, Movie.class);

        return movie;
    }
}
