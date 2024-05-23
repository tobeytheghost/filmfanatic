    // Import necessary JavaFX and Java libraries
   import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.GridPane;
    import javafx.stage.Stage;
//@FXML fields exported from SceneBuilder
    public class LoginController {

        @FXML
        private TextField usernameField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Label errorLabel;

        @FXML
        private GridPane loginGridPane;

        // Placeholder authentication logic, replace with your actual authentication
        private boolean performAuthentication(String username, String password) {
            // Your authentication logic here
            return username.equals("") && password.equals("");
        }

        @FXML
        private void handleLogin() {
            String username = usernameField.getText(); //grab the username from the FXML
            String password = passwordField.getText(); //grab the password from the FXML

            boolean isAuthenticated = performAuthentication(username, password); //call isAuthenticated function

            if (isAuthenticated) { //if authenticated then it loads the MainPage via function
                loadMainPage();
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        }

        private void loadMainPage() {
            try {
                // Replace with your main page FXML and controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); //load the main page
                Parent homePageRoot = loader.load();
                Scene homePageScene = new Scene(homePageRoot);

                // Get the current stage and set the scene to the home page scene
                Stage primaryStage = (Stage) loginGridPane.getScene().getWindow();
                primaryStage.setScene(homePageScene);
                //set the stage size to maximized
                primaryStage.setMaximized(true); // Max
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
