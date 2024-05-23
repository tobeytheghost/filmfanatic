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

// The WatchlistController class represents the controller for the watchlist view in a JavaFX application
public class WatchlistController {

    // Define JavaFX components using @FXML annotations
    @FXML
    private Button addMovieButton;

    @FXML
    private Button backButton;

    @FXML
    private Button removeMovieButton;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<WatchlistMovie> watchlistTableView;

    @FXML
    private TableColumn<WatchlistMovie, String> titleCol;

    @FXML
    private TableColumn<WatchlistMovie, String> genreCol;

    @FXML
    private TableColumn<WatchlistMovie, Double> runtimeCol;

    @FXML
    private TableColumn<WatchlistMovie, String> platformCol;

    // ObservableList to hold Movie objects in the watchlist
    private ObservableList<WatchlistMovie> watchlist = FXCollections.observableArrayList();

 // Method to save watched movies to a file
    private void saveMoviesToFile1() {
        //BufferedWriter to write text to the File "watchlist_mvoies.txt"
        //Exception handling used if the file does not exist then it will print error in terminal
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("watchlist_movies.txt"))) {
            for (WatchlistMovie movie1 : watchlist) {
                //for loop that gets the data from the application and adds it to the file
                writer1.write(movie1.getTitle() + "," + movie1.getGenre() + "," + movie1.getPlatform() + "," + movie1.getRuntime());
                writer1.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to load watched movies from a file
    private void loadMoviesFromFile1() {
        // watchlist.clear();// Clear the existing watchlist before loading
        //exception handling to catch if the file does not exist and the application can not load and read the file
        try (BufferedReader reader1 = new BufferedReader(new FileReader("watchlist_movies.txt"))) {
            String line;
            while ((line = reader1.readLine()) != null) { //if the line is not null then
                String[] parts1 = line.split(","); //add the variables to the different parts of the table 
                if (parts1.length == 4) {
                    String title = parts1[0];
                    String genre = parts1[1];
                    String platform = parts1[2];
                    double runtime = Double.parseDouble(parts1[3]); //parse the string to variable 
                    WatchlistMovie movie1 = new WatchlistMovie(title, genre, platform, runtime);  //add the information from the text and load it into the table and in the object
                    watchlist.add(movie1); //add each movie to the array list 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    // The initialize method is called when the FXML file is loaded
    public void initialize() {
        // Set up the TableView columns to display Movie properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        platformCol.setCellValueFactory(new PropertyValueFactory<>("platform"));
        runtimeCol.setCellValueFactory(new PropertyValueFactory<>("runtime"));

        //add the remove movie button
        removeMovieButton.setDisable(true);

        watchlistTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            removeMovieButton.setDisable(newSelection == null);
        });

        loadMoviesFromFile1();

        // Bind the watchlist to the TableView to display Movie objects
        watchlistTableView.setItems(watchlist);
    }

    // The addMovieButtonClicked method handles adding a new movie to the watchlist
    @FXML
    void addMovieButtonClicked(ActionEvent event) {
        // Create and configure a dialog box for entering movie details
        Dialog<WatchlistMovie> dialog = new Dialog<>();
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
        TextField runtimeField = new TextField();
        runtimeField.setPromptText("Runtime (Hours:Minutes)");

        // Add input fields to the dialog's content
        dialog.getDialogPane().setContent(new VBox(8, titleField, genreField, platformField, runtimeField));

        // Set up the result converter to handle user input when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String title = titleField.getText();
                String genre = genreField.getText();
                double runtime = Double.parseDouble(runtimeField.getText());
                String platform = platformField.getText();

                // Return a new Movie object with the entered details
                return new WatchlistMovie(title, genre, platform, runtime);
            }
            return null;
        });

        // Show the dialog and add the new movie to the watchlist if it was entered
        dialog.showAndWait().ifPresent(movie1 -> {
            if (movie1 != null) {
                // Add the movie to your watched movies list
                // You can replace 'watchlist' with the appropriate collection for your watched movies
                watchlist.add((WatchlistMovie)movie1);
                saveMoviesToFile1();//call the saveMovies to File function which will add the new entry to the loader
                
            }
        });

        
    }

    @FXML 
    void removeMovieButtonClicked(ActionEvent event) {
                //take the selected Movie from the table and remove it by calling the button
        WatchlistMovie selectedMovie = watchlistTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null){
            watchlist.remove(selectedMovie);
            saveMoviesToFile1(); //save the new data into the file 
        }
    }

@FXML
void openFilterOptions(ActionEvent event) {
    // Create a context menu for filter options
    ContextMenu filterMenu = new ContextMenu();

    // Create menu items for different filters
    MenuItem filterByTitle = new MenuItem("Filter by Title (Alphabetically)");
    filterByTitle.setOnAction(e -> filterMovies("title"));

    MenuItem filterByGenre = new MenuItem("Filter by Genre (Alphabetically)");
    filterByGenre.setOnAction(e -> filterMovies("genre"));

    MenuItem filterByRuntime = new MenuItem("Filter by Runtime (Numerically)");
    filterByRuntime.setOnAction(e -> filterMovies("runtime"));

    MenuItem filterByPlatform = new MenuItem("Filter by Platform (Alphabetically)");
    filterByPlatform.setOnAction(e -> filterMovies("platform"));

    // Add menu items to the filter menu
    filterMenu.getItems().addAll(filterByTitle, filterByGenre, filterByRuntime, filterByPlatform);

    // Show the filter menu
    filterMenu.show(filterButton, Side.BOTTOM, 0, 0);
}



private void filterMovies(String filterCriterion) {
    ObservableList<WatchlistMovie> filteredList = FXCollections.observableArrayList(watchlist);

    switch (filterCriterion) {
        case "title":
            filteredList.sort(Comparator.comparing(WatchlistMovie::getTitle));
            break;
        case "genre":
            filteredList.sort(Comparator.comparing(WatchlistMovie::getGenre));
            break;
        case "runtime":
            filteredList.sort(Comparator.comparingDouble(WatchlistMovie::getRuntime));
            break;
        case "platform":
            filteredList.sort(Comparator.comparing(WatchlistMovie::getPlatform));
            break;
        default:
            break;
    }

     // Update the TableView with the filtered list
    watchlistTableView.setItems(filteredList);
}


    // The backButtonClicked method handles navigating back to the home page
    @FXML
    void backButtonClicked(ActionEvent event) {
        try {
            // Load the HomePage.fxml file and create a new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent homePageRoot = loader.load();
            Scene homePageScene = new Scene(homePageRoot);

            // Get the current stage and set the scene to the home page scene
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setMaximized(true);
            stage.setScene(homePageScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
