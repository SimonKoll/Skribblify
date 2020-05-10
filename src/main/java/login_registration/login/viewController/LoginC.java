package login_registration.login.viewController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import dashboard.viewController.DashboardC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import login_registration.model.Status;
import login_registration.model.User;
import login_registration.registration.viewController.RegisterC;

public class LoginC {
    private Statement statement;
    private User model;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField uname;
    @FXML
    private Button login_button;
    @FXML
    private TextField pwd;
    @FXML
    private Button guest_button;
    @FXML
    private Button go_to_register_button;
    @FXML
    private static SVGPath svg_background;
    @FXML
    private Button register_button1;


    public LoginC() {

    }

    public static void show(Stage stage, Statement statement) {

        // Animation test

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
        String dbPwd = "";
        System.out.println("login_registration.login pressed");
        String sql
                = "select PASSWORD " +
                "from USERS " +
                "where USERNAME = " + "'" + inputPwd + "'";
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        System.out.println(rs);
        while(rs.next()) {
             dbPwd = rs.getString("PASSWORD");
            System.out.println(dbPwd);
        }
        if (dbPwd.equals(inputPwd)) {
            go_to_dashboard(event);
        }

        System.out.println("Login sucessfull!!!");

    }

    @FXML
    private void go_to_register(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;


        stage = (Stage) go_to_register_button.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/login_registration/registration/register.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        RegisterC.show(stage, this.statement);

    }

    @FXML
    private void go_to_dashboard(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;


        stage = (Stage) login_button.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/dashboard/DashboardV.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        DashboardC.show(stage, this.statement);

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

    public void guest_pressed(ActionEvent actionEvent) {
    }
}
