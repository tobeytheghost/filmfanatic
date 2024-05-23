import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FilmFanaticMain extends Application {

    public static String loginName; // Store the login name here

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load the LoginPage from the fxml sheet
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        //build the scene 
        Scene scene = new Scene(loader.load());
        //set the scene on the primary stage
        primaryStage.setScene(scene);
        //set the title of the primary stage
        primaryStage.setTitle("FilmFanatic");

        //size of stage when booting up
        primaryStage.setMaximized(true); // Max
        

        //Show the stage
        primaryStage.show();
    }
}
