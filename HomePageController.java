// Import necessary JavaFX and Java libraries
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//Contoller @FXML Classes Exported from Scene Builder Skeleton
public class HomePageController{

    @FXML
    private Label instructionLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button watchedMovieButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button watchlistButton;

    @FXML
    private Label welcomeLabel;


    //Action event for the Watched Movies Button
    @FXML
    void openWatchedMoviesPage(ActionEvent event) {
        //Used Exception Handling for opening the button. If the button did not open it would throw an IO Exception in the terminal but still display the main screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WatchedMovies.fxml")); //load the WatchedMovies Page
            Parent watchedMoviesRoot = loader.load();
            Scene watchedMoviesScene = new Scene(watchedMoviesRoot);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(watchedMoviesScene);
            stage.setMaximized(true); // Max
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Action event for the Watchlist Movies Button
    @FXML
    void openWatchlistPage(ActionEvent event) {
        //Used Exception Handling for opening the button. If the button did not open it would throw an IO Exception in the terminal but still display the main screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WatchlistPage.fxml")); //load the page
            Parent watchlistMoviesRoot = loader.load();
            Scene watchlistMovieScene = new Scene(watchlistMoviesRoot);
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(watchlistMovieScene);
            stage.setMaximized(true); // Max
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //I don't think we need the loginpage information, as this is on the main app
    //  @FXML
    // void openLoginPage(ActionEvent event) {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
    //         Parent loginRoot = loader.load();
    //         Scene loginScene = new Scene(loginRoot);
    //         Stage stage = (Stage) welcomeLabel.getScene().getWindow();
    //         stage.setScene(loginScene);
    //         stage.setMaximized(true);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

}
