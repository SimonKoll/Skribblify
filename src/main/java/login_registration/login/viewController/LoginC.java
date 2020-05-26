package login_registration.login.viewController;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import dashboard.viewController.DashboardC;
import dialog.Dialog;
import dialog.Navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import login_registration.model.User;
import login_registration.registration.viewController.RegisterC;

public class LoginC implements Dialog {
    private Statement statement;
    private User model;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField uname;
    @FXML
    private Button login_button;
    @FXML
    private PasswordField pwd;
    @FXML
    private Button guest_button;
    @FXML
    private Button go_to_register_button;
    @FXML
    private Tooltip loginTT;


    public LoginC() {

    }

    public void show(Stage stage, Statement statement, User user) {
        try {


            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("login_registration/login/LoginV.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Login");

            LoginC loginC = (LoginC) loader.getController();

            loginC.statement = statement;

            loginC.model = new User();
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with PersonV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    @FXML
    private void login_pressed(ActionEvent event) throws SQLException, IOException {
        String inputPwd = pwd.getText();
        String inputUsername = uname.getText();
        User user = new User();


        if (loginTT.getText().length() == 0) {

            String sql = "select * " +
                    "from USERS " +
                    "where USERNAME= " + "'" + inputUsername + "' AND PASSWORD = " + "'" + inputPwd + "'";

            ResultSet rs = statement.executeQuery(sql);

            rs.next();

            user.setUser_id(rs.getString("USER_ID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setUser_status(rs.getString("STATUS"));
            user.setUser_img(rs.getString("USER_IMG"));
            user.setPassword(rs.getString("PASSWORD"));

            if (user != null) {
                String sqlUpdate = "UPDATE USERS set STATUS='A' where USER_ID LIKE '" + user.getUser_id() + "'";
                this.statement.executeUpdate(sqlUpdate);
                System.out.println("Status of User '" + user.getUser_id() + "' updated to " + user.getUser_status());
            }

            Navigation.navigate(login_button, "/dashboard/DashboardV.fxml", this.statement, user, new DashboardC());
        }


    }

    @FXML
    private void checkEntries() throws SQLException {
        String inputPwd = pwd.getText();
        String inputUsername = uname.getText();


        String sql
                = "select * " +
                "from USERS " +
                "where USERNAME= " + "'" + inputUsername + "'";

        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            sql = "select * " +
                    "from USERS " +
                    "where USERNAME= " + "'" + inputUsername + "' AND PASSWORD = " + "'" + inputPwd + "'";
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                loginRegisterError("");
            } else {
                loginRegisterError("The Password is incorrect!");
            }
        } else {
            loginRegisterError("This User doesn't exist!");
        }

    }

    @FXML
    private void loginRegisterError(String errorString) {
        if (errorString.length() == 0) {
            loginTT.setOpacity(0);
        } else {
            loginTT.setOpacity(1);
        }
        loginTT.setText(errorString);
    }

    @FXML
    private void go_to_register(ActionEvent event) throws IOException {
        Navigation.navigate(go_to_register_button, "/login_registration/registration/register.fxml", this.statement, null, new RegisterC());
    }


    public Statement getStatement() {
        return statement;
    }

    public User getModel() {
        return model;
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

    public TextField getUname() {
        return uname;
    }

    public Button getLogin_button() {
        return login_button;
    }

    public TextField getPassword() {
        return pwd;
    }

    public Button getGuest_button() {
        return guest_button;
    }

    public Button getGo_to_register_button() {
        return go_to_register_button;
    }

    @FXML
    public void guest_pressed(ActionEvent actionEvent) {
    }
}
