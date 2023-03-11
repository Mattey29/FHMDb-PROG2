package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HomeController implements Initializable {
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
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Search Button wird geklickt - Liste wird gefiltert
        searchBtn.setOnAction(e -> {
            Genre selectedGenre = (Genre) genreComboBox.getValue();
            ObservableList<Movie> tempList = observableMovies;
            if (selectedGenre != null) {
                Predicate<Movie> genrePredicate = movie -> movie.getGenres().contains(selectedGenre);
                tempList = tempList.filtered(genrePredicate);
            }

            String searchTerm = searchField.getText();
            if (!searchTerm.isEmpty()) {
                Predicate<Movie> searchPredicate = movie -> movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase());
                tempList = tempList.filtered(searchPredicate);
            }

            movieListView.setItems(tempList);
            movieListView.setCellFactory(movieListView -> new MovieCell());

        });

        // Reset Button wird geklickt - Alle Filter zurücksetzen
        resetBtn.setOnAction(e -> {
            movieListView.setItems(observableMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell());

            genreComboBox.setValue(null);
            searchField.setText("");
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
            }
        });
    }

}