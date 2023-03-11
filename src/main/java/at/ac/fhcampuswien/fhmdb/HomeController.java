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
import java.util.Iterator;
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

        // Search Button wird geklickt - Liste wird gefiltert
        searchBtn.setOnAction(e -> {
            Genre selectedGenre = (Genre) genreComboBox.getValue();
            String searchTerm = searchField.getText().toLowerCase();

            // Create a new filtered list with the current observableMovies as the source list
            FilteredList<Movie> filteredList = new FilteredList<>(observableMovies);

            // Add a genre filter if a genre is selected
            if (selectedGenre != null) {
                filteredList.setPredicate(movie -> movie.getGenres().contains(selectedGenre));
            }

            // Add a title search filter if a search term is entered
            if (!searchTerm.isEmpty()) {
                filteredList.setPredicate(movie -> movie.getTitle().toLowerCase().contains(searchTerm));
            }

            // Set the filtered list as the data source for the list view
            movieListView.setItems(filteredList);
            movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
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
                // Sort observableMovies ascending
                FXCollections.sort(observableMovies, (m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
                sortBtn.setText("Sort (desc)");
            } else {
                // Sort observableMovies descending
                FXCollections.sort(observableMovies, (m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
                sortBtn.setText("Sort (asc)");
            }
        });
    }

}