package login.viewController;

import java.io.IOException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.model.Login;

public class LoginC {
    private Statement statement;
    private Login model;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField uname;
    @FXML
    private Button login_button;
    @FXML
    private TextField password;
    @FXML
    private Button register_button;
    @FXML
    private static SVGPath svg_background;
    @FXML
    private Button register_button1;





    public LoginC(){

    }
    public static void animationBackground(){
        PathTransition pathTransition = new PathTransition();

        String typicalDuration = "1000";
        pathTransition.setDuration(Duration.valueOf(typicalDuration));
        pathTransition.setDuration(new Duration((double)typicalDuration));
        pathTransition.setNode(circle);
        circle.setRotate(30);
        //pathTransition.setPath(AnimationPath.createFromPath(path));
        pathTransition.setPath(path);
        pathTransition.setOrientation(OrientationType.NONE);

        timeline.setCycleCount(3);
        timeline.setAutoReverse(true);
    }
    public static void show(Stage stage, Statement statement){

        // Animation test
        animationBackground();


        try{



            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("login/LoginV.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if(stage == null){
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Login");

            LoginC loginC = (LoginC) loader.getController();
            loginC.statement = statement;

            loginC.model = new Login();
            stage.show();

        }catch (IOException ex){
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with PersonV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    @FXML
    private void login_pressed(ActionEvent event) {
        System.out.println("login pressed");
        /*try{
            new Login(model).save(statement);
            model.clear();
            getUname().setText("Ok, gesichert!");
            getUname().setText("-fx-text-inner-color:green;");
        }catch(Exception ex){
            getUname().setText(ex.getMessage());
            getUname().setText("-fx-text-inner-color:red;");
        }
*/

    }

    @FXML
    private void go_to_register(ActionEvent event) {
    }

    public Statement getStatement() {
        return statement;
    }

    public Login getModel() {
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
        return password;
    }

    public Button getRegister_button() {
        return register_button;
    }
}
