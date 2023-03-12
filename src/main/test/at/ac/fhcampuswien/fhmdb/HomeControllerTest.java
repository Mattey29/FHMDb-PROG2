package at.ac.fhcampuswien.fhmdb;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;

class HomeControllerTest {
    /*@Test
    public void test_Reset() {

    }

    @Test
    public void no_Filter(){
        //given: which values get set?
        HomeController homeController = new HomeController();

        private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        FilteredList<Movie> filteredListbefore = new FilteredList<>(observableMovies);

        //when: Function call with Parameter
        homeController.filterMovies(null, "");

        //then
        private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        FilteredList<Movie> filteredListafter = new FilteredList<>(observableMovies);


        assertEquals(filteredListbefore, filteredListafter);


    }

    @Test
    public void testFilterMoviesByGenre(){}

    @Test
    public void test_Filter_Movies_By_Titel_Batman(){

    }

    @Test
    public void testFilterMoviesByGenreAndTitle(){}*/

    @Test
    public void test_Sort_Movies_Asc(){
        HomeController h1 = new HomeController();

        h1.sortBtn.setText("Sort (asc)");
        h1.sortMovies();

        String result = h1.sortBtn.getText();
        String expected = "Sort (desc)";

        assertEquals(expected, result);
    }

}