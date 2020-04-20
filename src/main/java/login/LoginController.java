package login;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController extends Application {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane btnLogin;
    @FXML
    private TextField uname;



    Stage window;
    Scene login, register;

    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    private TextField uname1;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        //Button 1
        Label label1 = new Label("Welcome to the login!");
        Button goToRegister = new Button("Go to register");
        goToRegister.setOnAction(e -> window.setScene(register));

        //Layout 1 - children laid out in vertical column
        AnchorPane layout1 = new AnchorPane();
        layout1.getChildren().addAll(label1, goToRegister);
        login = new Scene(layout1, 960, 540);


        //Button 2
        Button goToLogin = new Button("This sucks, go back login");
        goToLogin.setOnAction(e -> window.setScene(login));

        //Layout 2
        AnchorPane layout2 = new AnchorPane();
        layout2.getChildren().add(goToLogin);
        register = new Scene(layout2, 960, 540);

        //Display scene 1 at first
        window.setScene(login);
        window.setTitle("Login");
        window.show();
    }

    @FXML
    private void loadRegister(ActionEvent event) {
    }


}
