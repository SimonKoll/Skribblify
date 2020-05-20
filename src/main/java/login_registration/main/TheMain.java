package login_registration.main;


import dashboard.viewController.DashboardC;
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
    public  void stop() throws SQLException {
        if(DashboardC.user != null) {
            String sql = "SELECT * FROM users";
            Connection connection = DriverManager.getConnection(url, user, pwd);
            PreparedStatement stmt = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                if(rs.getString("USER_ID").equals(DashboardC.user.getUser_id())) {
                    rs.updateString("STATUS", "O");
                    rs.updateRow();
                }


            }
        }


    }
}