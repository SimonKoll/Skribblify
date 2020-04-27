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
import login_registration.login.viewController.LoginC;
import login_registration.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Statement statement;
    private User model;


    public RegisterC() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void register_pressed(ActionEvent event) throws SQLException {
        register();
    }

    @FXML
    private void goToLogin(ActionEvent event) {
    }

    private void register() throws SQLException {
        String newusername = username.getText();
        String newpassword_1 = password_1.getText();
        String newpassword_2 = password_2.getText();


        if(newpassword_1 == newpassword_2){
            User.newToDB(newusername,newpassword_1, this.statement);

        }

    }

    public static void show(Stage stage, Statement statement){

        // Animation test

        try{



            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("login_registration.registration/register.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if(stage == null){
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Register");

            RegisterC registerC = (RegisterC) loader.getController();
            registerC.statement = statement;

            registerC.model = new User();
            stage.show();

        }catch (IOException ex){
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with PersonV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }


}



























