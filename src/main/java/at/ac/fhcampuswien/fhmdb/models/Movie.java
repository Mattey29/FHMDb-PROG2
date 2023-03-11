package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        List<Genre> toyStoryGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.COMEDY);
        movies.add(new Movie("Toy Story", "A cowboy doll and his friends come to life when humans aren't around", toyStoryGenres));

        List<Genre> avengersGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Avengers: Endgame", "The Avengers attempt to reverse the damage caused by Thanos", avengersGenres));

        List<Genre> theLionKingGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.DRAMA);
        movies.add(new Movie("The Lion King", "A lion prince must overcome his own demons to become king", theLionKingGenres));

        List<Genre> theSocialNetworkGenres = Arrays.asList(Genre.DRAMA);
        movies.add(new Movie("The Social Network", "The story of how Mark Zuckerberg created Facebook", theSocialNetworkGenres));

        List<Genre> inceptionGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Inception", "A thief steals corporate secrets through the use of dream-sharing technology", inceptionGenres));

        List<Genre> forrestGumpGenres = Arrays.asList(Genre.COMEDY, Genre.DRAMA, Genre.ROMANCE);
        movies.add(new Movie("Forrest Gump", "Forrest Gump, a man with a low IQ, accomplishes great things in his life", forrestGumpGenres));

        List<Genre> theMartianGenres = Arrays.asList(Genre.ADVENTURE, Genre.DRAMA, Genre.SCIENCE_FICTION);
        movies.add(new Movie("The Martian", "An astronaut is mistakenly presumed dead and left behind on Mars", theMartianGenres));

        return movies;
    }
}
