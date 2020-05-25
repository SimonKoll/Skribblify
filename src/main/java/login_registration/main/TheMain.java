package login_registration.main;


import dashboard.viewController.DashboardC;
import dialog.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;
import login_registration.login.viewController.LoginC;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TheMain extends Application {
    String url = "jdbc:derby://localhost:1527/skribblify_database";
    String user = "app";
    String pwd = "app";
    @Override
    public void start(Stage stage) throws Exception {

        try{
            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();
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

            String sql = "UPDATE USERS set STATUS='O' where USER_ID LIKE '" + DashboardC.user.getUser_id() + "'";
            Connection connection = DriverManager.getConnection(url, user, pwd);


            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            }
        }
}