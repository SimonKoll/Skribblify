package dashboard.viewController;

import createLobby.CrobbyController;
import dialog.Dialog;
import dialog.Navigation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import login_registration.main.TheMain;
import login_registration.model.User;
import login_registration.login.viewController.LoginC;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardC implements Dialog {


    private Statement statement;

    @FXML
    private Pane gameHighlights01;
    @FXML
    private Button allPlayersBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button onlinePlayersBtn;
    @FXML
    private Button offlinePlayersBtn;
    @FXML
    private Button pendingPlayersBtn;
    @FXML
    private Text username;
    @FXML
    private Text userid;
    @FXML
    private Button playBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private ListView<String> friendsListView;
    @FXML
    private HBox charts;

    public static User user;

    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Pane scrollPane;

    public void show(Stage stage, Statement statement, User user) {
        try {

            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("dashboard/DashboardV.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }


            stage.setScene(scene);
            stage.setTitle("Skribblify - Login");


            DashboardC dashboardC = (DashboardC) loader.getController();
            dashboardC.init(user);
            dashboardC.statement = statement;
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with DashboardV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }


    private void init(User user) {
        this.user = user;
        if (user != null) {
            System.out.println(this.user.toString());
            userid.setText(this.user.getUser_id());
            username.setText(this.user.getUsername());
        }
        drawChart();
    }

    private void drawChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Jahr");

        final LineChart<String, Number> lineChart = new LineChart<String, Number>(
                xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String, Number>("2011", 15));
        series.getData().add(new XYChart.Data<String, Number>("2012", 21));
        series.getData().add(new XYChart.Data<String, Number>("2013", 23));
        series.getData().add(new XYChart.Data<String, Number>("2014", 17));
        series.getData().add(new XYChart.Data<String, Number>("2015", 27));
        series.getData().add(new XYChart.Data<String, Number>("2016", 33));
        Color color = Color.RED;
        String rgb = String.format("%d, %d, %d",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        lineChart.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");

        lineChart.getData().add(series);
        charts.getChildren().add(lineChart);
    }

    @FXML
    private void checkInput(KeyEvent event) {
    }

    private void insertIntoListView(String searchCase, String status) throws SQLException {

        switch (searchCase) {
            case "user":
                friendsListView.getItems().clear();
                String sqlUser = "select * from users u where user_id in (select user_one from friendship where USER_TWO='" + user.getUser_id() + "' AND friendship_status='" + status + "' UNION select user_two from friendship where USER_ONE='" + user.getUser_id() + "' AND friendship_status='" + status + "')";
                System.out.println(sqlUser);
                ResultSet rsUser = statement.executeQuery(sqlUser);
                System.out.println("statement returned values");
                insertFriends(rsUser);
                break;
            case "friendship":
                friendsListView.getItems().clear();
                String sqlFriendship = "select * from users u where STATUS = '" + status + "' and user_id in (select user_one from friendship where USER_TWO='" + user.getUser_id() + "' AND friendship_status='E' UNION select user_two from friendship where USER_ONE='" + user.getUser_id() + "' AND friendship_status='E')";
                System.out.println(sqlFriendship);
                ResultSet rsFriendship = statement.executeQuery(sqlFriendship);
                System.out.println("statement returned values");
                insertFriends(rsFriendship);
                break;
        }


    }

    private void insertFriends(ResultSet rsFriendship) throws SQLException {
        if (!rsFriendship.next()) {
            friendsListView.getItems().add("No friends found :(");
        } else {
            String first_friend = rsFriendship.getString("USERNAME");
            friendsListView.getItems().add(first_friend);
            while (rsFriendship.next()) {
                String friend_name = rsFriendship.getString("USERNAME");
                friendsListView.getItems().add(friend_name);
            }
        }
    }

    @FXML
    private void showAllPlayers(ActionEvent event) throws SQLException {
        insertIntoListView("user", "E");
    }


    @FXML
    private void addUsertoFriendlist(ActionEvent event) {
        if (friendsListView.getItems().contains("No friends found :(")) {
        } else {
            friendsListView.getSelectionModel().getSelectedItem();
            System.out.println(
                    friendsListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void showOnlinePlayers(ActionEvent event) throws SQLException {
        insertIntoListView("friendship", "A");
    }

    @FXML
    private void showOfflinePlayers(ActionEvent event) throws SQLException {
        insertIntoListView("friendship", "O");
    }

    @FXML
    private void showPendingPlayers(ActionEvent event) throws SQLException {
        insertIntoListView("user", "P");

    }

    @FXML
    private void logout(ActionEvent event) throws IOException, SQLException {
        Navigation.navigate(logoutBtn, "/login_registration/login/LoginV.fxml", this.statement, null, new LoginC());

        TheMain main = new TheMain();
        main.stop();
    }

    @FXML
    private void playGame(ActionEvent event) throws IOException {
        Navigation.navigate(playBtn, "/createLobby/CrobbyV.fxml", this.statement, this.user, new CrobbyController());
    }

    IntegerProperty scrollValue = new SimpleIntegerProperty(0);

    @FXML
    private void scrollEffect(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            if (scrollValue.getValue() > -520) {
                scrollValue.setValue(scrollValue.getValue() - 8);
            }
        } else {
            if (scrollValue.getValue() <= 0) {
                scrollValue.setValue(scrollValue.getValue() + 8);
            }
        }
        scrollPane.setLayoutY(scrollValue.getValue());
    }


}
