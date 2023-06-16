package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controller.Controller;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortingState;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    public JFXButton resetBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXComboBox<Integer> yearComboBox;

    @FXML
    public JFXComboBox<Double> ratingComboBox;

    public static List<Movie> watchList = new ArrayList<>();

    @FXML
    public JFXButton sortBtn;

    // Old method to load movies
    //public List<Movie> allMovies = Movie.initializeMovies();

    private final MovieAPI movieAPI = new MovieAPI();

    //public List<Movie> allMovies = movieAPI.fetchMovies();
    public List<Movie> allMovies = movieAPI.getMovies(null, null, null, null);

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public HomeController() {
        this.watchlistRepository = WatchlistRepository.getInstance();
    }
    public Controller controller;

    private final WatchlistRepository watchlistRepository;

    private SortingState state = SortingState.NOT_SORTED;

    /**
     * Wird aufgerufen, wenn der Controller initialisiert wird.
     * Hier werden die UI-Elemente initialisiert und die Daten geladen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);

        // Versuche, die Filme aus der Watchlist-Datenbank abzurufen
        try {
            List<WatchlistMovieEntity> entityList = watchlistRepository.getAll();
            watchList = WatchlistMovieEntity.convertToMovieList(entityList);
            for (WatchlistMovieEntity movie : entityList) {
                String movieString = movie.toString();
                System.out.println(movieString);
            }
        } catch (DatabaseException | IOException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", e.getMessage());
        }

        // Versuche, die Watchlist-Datenbank zu füllen
        try {
            fillWatchlist(watchlistRepository.getAll());
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", e.getMessage());
        }

        // Setze die Filme in die ListView
        movieListView.setItems(observableMovies);

        // Erstelle den Controller für die Button-Klick-Events
        try {
            this.controller = new Controller();
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }

        // Verwende eine benutzerdefinierte Zellenfabrik für die Anzeige der Filme in der ListView
        movieListView.setCellFactory(movieListView -> new MovieCell(controller.getOnAddToWatchlistClicked(), controller.getOnRemoveFromWatchlistClicked(), true, this));

        // Initialisiere die UI-Elemente
        searchField.setText("");

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        yearComboBox.setPromptText("Filter by Release Year");
        List<Integer> allReleaseYears = new ArrayList<>();
        for (Movie movie : allMovies) {
            int releaseYear = movie.getReleaseYear();
            if (!allReleaseYears.contains(releaseYear)) {
                allReleaseYears.add(releaseYear);
            }
            Collections.sort(allReleaseYears);
        }
        yearComboBox.getItems().addAll(allReleaseYears);

        ratingComboBox.setPromptText("Filter by Rating");
        List<Double> allRatings = new ArrayList<>();
        for (double i = 5.0; i < 10.0; i = i + 0.5) {
            allRatings.add(i);
        }
        ratingComboBox.getItems().addAll(allRatings);

        // Setze die Aktion für den Suchen-Button
        searchBtn.setOnAction(e -> {
            // Filtere die Filme basierend auf den ausgewählten Kriterien
            Genre selectedGenre = genreComboBox.getValue();
            String searchTerm = searchField.getText().toLowerCase();
            Integer releaseYear = yearComboBox.getValue();
            Double lookupRatingFrom = ratingComboBox.getValue();
            List<Movie> filterMovies = null;
            filterMovies = movieAPI.getMovies(searchTerm, selectedGenre, releaseYear, lookupRatingFrom);
            observableMovies.clear();
            observableMovies.addAll(filterMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell(controller.getOnAddToWatchlistClicked(), controller.getOnRemoveFromWatchlistClicked(), true, this));
        });

        // Setze die Aktion für den Zurücksetzen-Button
        resetBtn.setOnAction(e -> {
            // Setze alle Filter zurück
            this.reset(false);
        });

        // Setze die Aktion für den Sortieren-Button
        sortBtn.setOnAction(actionEvent -> {
            this.sortMovies(observableMovies);
        });
    }

    /**
     * Sortiert die Filme in der ListView.
     */
    public void sortMovies(ObservableList<Movie> mov) {
        if (state == SortingState.ASCENDING) {
            // Sortiere die Filme aufsteigend
            this.state = SortingState.DESCENDING;
            sortList(mov, true);
            sortBtn.setText("Sort (desc)");
        } else {
            // Sortiere die Filme absteigend
            this.state = SortingState.ASCENDING;
            sortList(mov, false);
            sortBtn.setText("Sort (asc)");
        }
    }


    /**
     * Sortiert die Liste der Filme.
     */
    public void sortList(ObservableList<Movie> mov, boolean ascendingOrder) {
        if (ascendingOrder) {
            // Sortiere aufsteigend
            FXCollections.sort(mov, (m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        } else {
            // Sortiere absteigend
            FXCollections.sort(mov, (m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        }
    }

    /**
     * Setzt die Filter zurück und zeigt alle Filme an.
     */
    public void reset(boolean keepValues) {
        // Setze die ListView auf alle Filme zurück
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell(controller.getOnAddToWatchlistClicked(), controller.getOnRemoveFromWatchlistClicked(), true, this));

        // Setze die Alphabetische Sortierung zurück
        observableMovies.clear();
        observableMovies.addAll(allMovies);

        // Setze die Filter zurück
        if (!keepValues) {
            genreComboBox.setValue(null);
            yearComboBox.setValue(null);
            ratingComboBox.setValue(null);
            searchField.setText("");
        }
    }

    public List<Movie> filterMovies(Genre selectedGenre, String searchTerm, ObservableList<Movie> mov){
        //FilteredList<Movie> filteredList = new FilteredList<Movie>(mov);
        ObservableList<Movie> copiedList = FXCollections.observableArrayList(mov); //shallow copy
        FilteredList<Movie> filteredList = new FilteredList<>(copiedList);

        Predicate<Movie> genrePredicate = movie -> movie.getGenres().contains(selectedGenre);

        Predicate<Movie> titlePredicate = movie -> movie.getTitle().toLowerCase().contains(searchTerm);
        Predicate<Movie> descriptionPredicate = movie -> movie.getDescription().toLowerCase().contains(searchTerm);

        Predicate<Movie> titleAndDescPredicate = titlePredicate.or(descriptionPredicate); //SearchTerm has to be in Title OR in Description (also in both counts)
        Predicate<Movie> combinedPredicate = genrePredicate.and(titleAndDescPredicate); //Movie has to be the filtered Genre & contain the SearchTerm in the Title OR Description

        if (selectedGenre != null && !searchTerm.isEmpty()) {
            //if Genre is selected && a SearchTerm is given
            filteredList.setPredicate(combinedPredicate);
        } else if (selectedGenre != null) {
            // Add a genre filter if a genre is selected
            filteredList.setPredicate(genrePredicate);
        } else if (!searchTerm.isEmpty()) {
            // Add a title search filter if a search term is entered
            filteredList.setPredicate(titleAndDescPredicate);

        } else {
            // No filter applied, show all movies
            //filteredList = (FilteredList<Movie>) mov;
        }

        mov.clear();
        mov.addAll(filteredList);

        return filteredList;

        //movieListView.setItems(filteredList);
        //movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
    }

    public void setTextSortBtn(String text){
        sortBtn.setText(text);
    }

    public String getTextSortBtn(){
        return sortBtn.getText();
    }

    public String getMostPopularActor(List<Movie> movies) {
        // Flatten the main cast of all movies into a single list of actors
        List<String> actors = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.toList());

        // Count the occurrences of each actor and return the one with the highest count
        return actors.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(IllegalStateException::new)
                .getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        // Find the length of the longest movie title using a stream
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        // Count the number of movies with a certain director using a stream
        return movies.stream()
                .filter(movie -> movie.getDirectors().stream().anyMatch(d -> d.equals(director)))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        // Filter the movies that were released between two years using a stream
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    /**
     * Wird aufgerufen, wenn auf den "Watchlist" Button geklickt wird.
     * Wechselt zur Ansicht der Watchlist, falls vorhanden, andernfalls wird eine Information angezeigt.
     */
    @FXML
    private void switchToWatchlist() {
        if (watchList.isEmpty()) {
            MovieAlert.showAlert(Alert.AlertType.INFORMATION, "Hinweis", "", "Ihre Watchlist ist leer. Bitte fügen Sie Filme hinzu und versuchen Sie es erneut!");
        } else {
            updateListView(watchList, false);
        }
    }

    /**
     * Wird aufgerufen, wenn auf den "Home" Button geklickt wird.
     * Wechselt zur Hauptansicht und zeigt alle Filme an.
     */
    @FXML
    private void switchToHome(ActionEvent event) {
        updateListView(allMovies, true);
    }

    /**
     * Aktualisiert die ListView mit den gegebenen Filmen.
     */
    public void updateListView(List<Movie> viewContent, boolean isHomeView) {
        observableMovies.clear();
        observableMovies.addAll(viewContent);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell(controller.getOnAddToWatchlistClicked(), controller.getOnRemoveFromWatchlistClicked(), isHomeView, this));
    }

    /**
     * Füllt die Watchlist mit den gegebenen WatchlistMovieEntity-Objekten.
     */
    public List<Movie> fillWatchlist(List<WatchlistMovieEntity> entities) {
        List<Movie> watchlistMovies = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (WatchlistMovieEntity entity : entities) {
                watchlistMovies.add(entity);
            }
        }
        return watchlistMovies;
    }

    // STREAM TESTS
    public static void main(String[] args) throws SQLException, DatabaseException {
        List<Movie> requestedMovies;
        MovieAPI movieAPI1 = new MovieAPI();
        HomeController homeController = new HomeController();

        requestedMovies = movieAPI1.getMovies(null, null, null, null);

        String mostPopularActor = homeController.getMostPopularActor(requestedMovies);
        System.out.println("Most popular actor: " + mostPopularActor);

        int longestTitle = homeController.getLongestMovieTitle(requestedMovies);
        System.out.println("The longest Title: " + longestTitle);

        double spielbergMovies = homeController.countMoviesFrom(requestedMovies, "Steven Spielberg");
        System.out.println("Steven Spielberg movies: " + spielbergMovies);

        List<Movie> ninetiesMovies = homeController.getMoviesBetweenYears(requestedMovies, 1990, 1990);
        System.out.println("Movies in 1990: ");
        for (Movie movie : ninetiesMovies) {
            System.out.println("    - " + movie.getTitle()+" in "+movie.getReleaseYear());
        }
    }
}