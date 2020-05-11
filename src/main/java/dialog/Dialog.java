package dialog;

import javafx.stage.Stage;
import login_registration.model.User;

import java.sql.Statement;

public interface Dialog {
    void show(Stage stage, Statement statement, User user);
}
