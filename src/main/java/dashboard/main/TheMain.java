package dashboard.main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dashboard.viewController.DashboardC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TheMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try{
            String url = "jdbc:derby://localhost:1527/skribblify_database";
            String user = "app";
            String pwd = "app";

            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();

            DashboardC.show(stage, statement);
        }
        catch(SQLException ex){
            Logger.getLogger(TheMain.class.getName()).log(Level.SEVERE,null,ex);
            System.exit(1);
        }

    }
    public void main(String[] args) {
        launch(args);
    }
}