package login_registration.registration.viewController;

import createLobby.CrobbyController;
import dashboard.viewController.DashboardC;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.client.GameC;
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
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterC implements Initializable, Dialog {
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
    private void register_pressed(ActionEvent event) throws SQLException, ParseException, IOException {
        register(event);

    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Navigation.navigate(goToLoginBtn, "/login_registration/login/LoginV.fxml", this.statement, null, new LoginC());
    }

    private void register(ActionEvent event) throws SQLException, ParseException, IOException {
        String newusername = username.getText();
        String newpassword_1 = password_1.getText();
        String newpassword_2 = password_2.getText();



        String sql
                = "select * " +
                "from USERS " +
                "where USERNAME = " + "'" + newusername + "'";

        ResultSet rs = statement.executeQuery(sql);

        if(rs.next()){


                loginRegisterError("This User already exist.");
            }else{
            if (newpassword_1.equals(newpassword_2)) {

                User.newToDB(newusername, newpassword_1, this.statement);
                goToLogin(event);
            }else{
                loginRegisterError("The Passwords don't match.");
            }


        }






    }

    @FXML
    private void loginRegisterError(String errorString){
        System.out.println(errorString);
    }

    public  void show(Stage stage, Statement statement, User user) {

        // Animation test

        try {


            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("login_registration/registration/register.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Register");

            RegisterC registerC = (RegisterC) loader.getController();
            registerC.statement = statement;

            registerC.model = new User();
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with PersonV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }


}



























