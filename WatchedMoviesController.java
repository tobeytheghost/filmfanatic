// Import necessary JavaFX and Java libraries
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;


import javafx.scene.control.cell.PropertyValueFactory;

public class WatchedMoviesController {

    @FXML
    private Button addMovieButton;

    @FXML
    private Button removeMovieButton;

    @FXML
    private Button backButton;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<WatchedMovie> watchedmoviesTableView;

    @FXML
    private TableColumn<WatchedMovie, String> genreCol;

    @FXML
     private TableColumn<WatchedMovie, Double> ratingCol;

    @FXML
     private TableColumn<WatchedMovie, String> titleCol;

    @FXML
     private TableColumn<WatchedMovie, String> platformCol;

    @FXML
    private Label watchedMovieLabel;


    // ObservableList to hold Movie objects in the watchlist
    private ObservableList<WatchedMovie> watchedmovieslist = FXCollections.observableArrayList();


    // Method to save watched movies to a file
    private void saveMoviesToFile() {
        //BufferedWriter to write text to the File "watched_movies.txt"
        //Exception handling used if the file does not exist then it will print error in terminal
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("watched_movies.txt"))) {
            for (WatchedMovie movie : watchedmovieslist) { 
                //for loop that gets the data from the application and adds it to the file
                writer.write(movie.getTitle() + "," + movie.getGenre() + "," + movie.getPlatform() + "," + movie.getRating());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to load watched movies from a file
    private void loadMoviesFromFile() {
        // watchedmovieslist.clear(); // Clear the existing watchlist before loading
        //exception handling to catch if the file does not exist and the application can not load and read the file
        try (BufferedReader reader = new BufferedReader(new FileReader("watched_movies.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) { //if the line is not null then
                String[] parts = line.split(","); //add the variables to the different parts of the table 
                if (parts.length == 4) {
                    String title = parts[0];
                    String genre = parts[1];
                    String platform = parts[2];
                    double rating = Double.parseDouble(parts[3]); //parse the string to variable 
                    WatchedMovie movie = new WatchedMovie(title, genre, platform, rating); //add the information from the text and load it into the table and in the object
                    watchedmovieslist.add(movie); //add each movie to the array list 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//initialize the table
    public void initialize() {
        // Set up the TableView columns to display Movie properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        platformCol.setCellValueFactory(new PropertyValueFactory<>("platform"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        //add the remove movie button
        removeMovieButton.setDisable(true);

        watchedmoviesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            removeMovieButton.setDisable(newSelection == null);
        });

        loadMoviesFromFile(); //load the Movies from the file

        // Bind the watchlist to the TableView to display Movie objects
        watchedmoviesTableView.setItems(watchedmovieslist); // This line connects your watchlist array to the TableView
        // watchedmovieslist.addAll(MovieDataStorage.loadMovies()); // For WatchedMoviesController

    }


    @FXML
    void addMovieButtonClicked(ActionEvent event) {
        // Create and configure a dialog box for entering movie details
        Dialog<Movie> dialog = new Dialog<>();
        ((Stage)dialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        dialog.setTitle("Add Movie");
        dialog.setHeaderText("Enter Movie Details");
        
        // Add OK and CANCEL buttons to the dialog
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create input fields for movie details
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextField platformField = new TextField();
        platformField.setPromptText("Platform");
         TextField ratingField = new TextField();
        ratingField.setPromptText("Rating");

        // Add input fields to the dialog's content
        dialog.getDialogPane().setContent(new VBox(8, titleField, genreField, platformField, ratingField));

        // Set up the result converter to handle user input when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {

                String title = titleField.getText();
                String genre = genreField.getText();
                double rating = Double.parseDouble(ratingField.getText());
                String platform = platformField.getText();

                // Return a new Movie object with the entered details
                return new WatchedMovie(title, genre, platform, rating );
            }
            return null;
        });

        // Show the dialog and add the new movie to your watched movies list if it was entered
        dialog.showAndWait().ifPresent(movie -> {
            if (movie != null) {
                // Add the movie to your watched movies list
                // You can replace 'watchlist' with the appropriate collection for your watched movies
                watchedmovieslist.add((WatchedMovie) movie);
                saveMoviesToFile(); //call the saveMovies to File function which will add the new entry to the loader
                
            }
        });;
    }

    @FXML
    void removeMovieButtonClicked (ActionEvent event) {
        //take the selected Movie from the table and remove it by calling the button
        WatchedMovie selectedMovie = watchedmoviesTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null){
            watchedmovieslist.remove(selectedMovie);
            saveMoviesToFile(); //save the new data into the file 
        }
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        //code to go back to the home page after clicking the back button
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent homePageRoot1 = loader1.load();
            Scene homePageScene1 = new Scene(homePageRoot1);

            Stage stage1 = (Stage) watchedMovieLabel.getScene().getWindow();
            stage1.setScene(homePageScene1);
            stage1.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
void openFilterOptions(ActionEvent event) {
    // Create a menu for filter options
    ContextMenu filterMenu = new ContextMenu();

    // Create menu items for different filters
    MenuItem filterByName = new MenuItem("Filter by Name (Alphabetically)");
    filterByName.setOnAction(e -> filterMovies("name"));

    MenuItem filterByGenre = new MenuItem("Filter by Genre (Alphabetically)");
    filterByGenre.setOnAction(e -> filterMovies("genre"));

    MenuItem filterByRating = new MenuItem("Filter by Rating (Numerically)");
    filterByRating.setOnAction(e -> filterMovies("rating"));

    MenuItem filterByPlatform = new MenuItem("Filter by Platform (Alphabetically)");
    filterByPlatform.setOnAction(e -> filterMovies("platform"));

    // Add menu items to the filter menu
    filterMenu.getItems().addAll(filterByName, filterByGenre, filterByRating, filterByPlatform);

    // Show the filter menu when the filter button is clicked
    filterMenu.show(filterButton, Side.BOTTOM, 0, 0);
}


    private void filterMovies(String filterCriterion) {
    // Create a filtered list based on the selected filter criterion
    ObservableList<WatchedMovie> filteredList = FXCollections.observableArrayList(watchedmovieslist);

    // Apply the appropriate comparator based on the selected filter criterion
    switch (filterCriterion) {
        case "name":
            filteredList.sort(Comparator.comparing(WatchedMovie::getTitle));
            break;
        case "genre":
            filteredList.sort(Comparator.comparing(WatchedMovie::getGenre));
            break;
        case "rating":
            filteredList.sort(Comparator.comparingDouble(WatchedMovie::getRating));
            break;
        case "platform":
            filteredList.sort(Comparator.comparing(WatchedMovie::getPlatform));
            break;
        default:
            break;
    }

    // Update the TableView with the filtered list
    watchedmoviesTableView.setItems(filteredList);
}


}
