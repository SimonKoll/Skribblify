package login_registration.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class LoginRegistrationController implements Initializable {

    private Button toLogin;
    @FXML
    private Button toRegister;
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
    @FXML
    private Button go_to_register_button;

    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goToLoginBtn){
            stage = (Stage) goToLoginBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/login/LoginV.fxml"));
        }
        else{
            stage = (Stage) go_to_register_button.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/registration/register.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
