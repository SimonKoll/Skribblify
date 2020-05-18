package createCode;

import javafx.stage.Stage;
import login_registration.model.User;

import java.sql.Statement;

public interface Code {
    String createCode(String type, Statement statement);
}
