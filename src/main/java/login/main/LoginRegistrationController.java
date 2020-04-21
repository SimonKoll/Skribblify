package login.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginRegistrationController implements Initializable {

    @FXML
    private Label lbl1,lbl2;

    @FXML
    private Button toLogin,toRegister;

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==toLogin){
            stage = (Stage) toLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/login/LoginV.fxml"));
        }
        else{
            stage = (Stage) toRegister.getScene().getWindow();
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
}
