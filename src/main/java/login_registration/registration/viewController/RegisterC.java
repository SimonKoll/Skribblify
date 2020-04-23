package login_registration.registration.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterC implements Initializable {
    @FXML
    private Button toLogin;

    @FXML
    private Button registerBtn;
    @FXML
    private TextField password_2;
    @FXML
    private TextField password_1;
    @FXML
    private TextField username;
    @FXML
    private Button goToLoginBtn;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void register_pressed(ActionEvent event) {
    }

    @FXML
    private void goToLogin(ActionEvent event) {
    }
}
