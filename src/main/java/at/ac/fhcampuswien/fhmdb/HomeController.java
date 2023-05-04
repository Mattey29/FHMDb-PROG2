package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    public JFXButton searchBtn;
    public JFXButton resetBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox<Integer> yearComboBox;

    @FXML
    public JFXComboBox<Double> ratingComboBox;



    @FXML
    public JFXButton sortBtn;

    // Old method to load movies
    //public List<Movie> allMovies = Movie.initializeMovies();

    private MovieAPI movieAPI = new MovieAPI();

    //public List<Movie> allMovies = movieAPI.fetchMovies();
    public List<Movie> allMovies = movieAPI.getMovies(null, null, null, null);

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public HomeController() throws IOException {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        searchField.setText("");

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        yearComboBox.setPromptText("Filter by Release Year");
        List<Integer> allReleaseYears = new ArrayList<>();
        for (Movie movie : allMovies) {
            int releaseYear = movie.getReleaseYear();
            if(!allReleaseYears.contains(releaseYear)) {
                allReleaseYears.add(releaseYear);
            }
            Collections.sort(allReleaseYears);

        }
        yearComboBox.getItems().addAll(allReleaseYears);

        ratingComboBox.setPromptText("Filter by Rating");
        List<Double> allRatings = new ArrayList<>();
        for (double i = 5.0; i < 10.0; i=i+0.5) {
                allRatings.add(i);
        }

        ratingComboBox.getItems().addAll(allRatings);

        try {
            VBox drawerContent = FXMLLoader.load(getClass().getResource("/at/ac/fhcampuswien/fhmdb/drawer_content.fxml"));
            drawer.setSidePane(drawerContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(hamburger);
        burgerTask.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();

            if (drawer.isOpened()) {
                drawer.close();
                drawer.toBack(); // Send the drawer to the back when closed
            } else {
                drawer.open();
                drawer.toFront(); // Bring the drawer to the front when opened
            }
        });

        // Search Button gets clicked - List gets filtered
        searchBtn.setOnAction(e -> {


            Genre selectedGenre = (Genre) genreComboBox.getValue();


            String searchTerm = searchField.getText().toLowerCase();

            Integer releaseYear = yearComboBox.getValue();

            Double lookupRatingFrom = ratingComboBox.getValue();


            List<Movie> filterMovies = null;

            try {
                filterMovies = movieAPI.getMovies(searchTerm, selectedGenre, releaseYear, lookupRatingFrom);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            observableMovies.clear();
            observableMovies.addAll(filterMovies);

            //movieListView.setItems(observableMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
        });

        // Reset Button get clicked- All Filters get reseted
        resetBtn.setOnAction(e -> {
            this.reset(false);
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            this.sortMovies(observableMovies);
        });
    }

    public void sortMovies(ObservableList<Movie> mov) {
        if(sortBtn.getText().equals("Sort (asc)")) {
            // Sort observableMovies ascending
            sortList(mov, true);
            sortBtn.setText("Sort (desc)");
        } else {
            // Sort observableMovies descending
            sortList(mov, false);
            sortBtn.setText("Sort (asc)");
        }
    }

    public void sortList(ObservableList<Movie> mov, boolean ascendedOrder){
        if (ascendedOrder){
            FXCollections.sort(mov, (m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        }else {
            FXCollections.sort(mov, (m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        }

    }

    public void reset(boolean keepValues) {
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        //Alphabetical Sort will be reset
        observableMovies.clear();
        observableMovies.addAll(allMovies);

        if(!keepValues){
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

    // STREAM TESTS
    public static void main(String[] args) throws IOException {
        List<Movie> requestedMovies;
        MovieAPI movieAPI1 = new MovieAPI();
        HomeController homeController = new HomeController();

        try {
            requestedMovies = movieAPI1.getMovies(null, null, null, null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

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