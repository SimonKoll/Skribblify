package login_registration.main;


import dashboard.viewController.DashboardC;
import javafx.application.Application;
import javafx.stage.Stage;
import login_registration.login.viewController.LoginC;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TheMain extends Application {

    public Statement statement;
    @Override
    public void start(Stage stage) throws Exception {

        try{
            String url = "jdbc:derby://localhost:1527/skribblify_database";
            String user = "app";
            String pwd = "app";

            Connection connection = DriverManager.getConnection(url, user, pwd);
            statement = connection.createStatement();
            LoginC loginC = new LoginC();
            loginC.show(stage, statement, null);
        }
        catch(SQLException ex){
            Logger.getLogger(TheMain.class.getName()).log(Level.SEVERE,null,ex);
            System.exit(1);
        }

    }
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws SQLException {
        if(DashboardC.user != null) {
            String sql = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("USER_ID") == DashboardC.user.getUser_id()) {
                    rs.updateString("STATUS", "O");
                }
            }
        }


    }
}