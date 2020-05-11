package dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import login_registration.model.User;

import java.io.IOException;
import java.sql.Statement;

public class Navigation {
    public static void navigate(Button btn, String viewName, Statement statement, User user, Dialog c) throws IOException {
        Parent root;
        Stage stage;

        stage = (Stage) btn.getScene().getWindow();

        root = FXMLLoader.load(Navigation.class.getResource(viewName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        c.show(stage, statement, user);
    }
}
