package dashboard.viewController;

import createCode.CreateCode;
import createLobby.CrobbyController;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.server.util.ConsoleColor;
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
import javafx.util.StringConverter;
import login_registration.main.TheMain;
import login_registration.model.User;
import login_registration.login.viewController.LoginC;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.json.JSONObject;

import javax.xml.transform.Result;

public class DashboardC implements Dialog {

    @FXML
    private TextField searchField;

    private Statement statement;

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
            try {
                dashboardC.init(user);
            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
            dashboardC.statement = statement;
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with DashboardV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }

    @Override
    public void showPlacement(Stage stage, Statement statement, User user, JSONObject jso) {

    }


    private void init(User user) throws SQLException, ParseException {
        this.user = user;
        if (user != null) {
            System.out.println(this.user.toString());
            userid.setText(this.user.getUser_id());
            username.setText(this.user.getUsername());
        }
        drawChart();
    }

    private void drawChart() throws SQLException, ParseException {
        String url = "jdbc:derby://localhost:1527/skribblify_database";
        String user = "app";
        String pwd = "app";
        String query = "SELECT * from USERSTATS us join LOBBY L on us.LOBBY_ID = L.LOBBY_ID WHERE us.user_id like '" + userid.getText() + "'";
        ResultSet rSet;
        Connection connection;

        connection = DriverManager.getConnection(url, user, pwd);
        Statement statement = connection.createStatement();
        rSet = statement.executeQuery(query);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis(10, 0, 5.0);
//        System.out.println(yAxis.getTickUnit());
        xAxis.setLabel("Jahr");
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
                xAxis, yAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();


        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(final Number object) {
                return ".";
            }
        });

        boolean first = false;
        int dummyMillis = 0;
        int firstValue = 0;

        while (rSet.next()) {
            int placement = rSet.getInt("PLACEMENT");
            int maxPlayers = rSet.getInt("MAXPLAYERS");
            int rounds = rSet.getInt("ROUND_SIZE");
            String date = rSet.getString("CREATED");


            LocalDateTime localDateTime = LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            LocalDateTime now = LocalDateTime.now();
            localDateTime = localDateTime.minusMonths(1);
            //localDateTime = localDateTime.minusYears(50);

            long millis = now.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() - localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant().getEpochSecond();


            System.out.println("placement:" + placement + "," + "max players:" + maxPlayers + ", " + "Rounds:" + rounds + ", " + "played on:" + date);
            System.out.println(millis + "\n ==================");


            if (!first) {
                firstValue = (int) millis;
            }


            dummyMillis = (int) (millis - firstValue);


            series.getData().add(new XYChart.Data<Number, Number>(dummyMillis, placement));
            first = true;
        }

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
    private void checkInput(KeyEvent event) throws SQLException {
        friendsListView.getItems().clear();
        String searchID = searchField.getText();
        System.out.println(searchID);
        String sql = "select * from USERS " +
                "where user_id like '" + searchID + "%'" +
                "and not exists (" +
                "    select * from FRIENDSHIP" +
                "    where ((user_one='" + searchID + "' and user_two='" + user.getUser_id() + "')" +
                "       OR (user_one='" + user.getUser_id() + "' and user_two='" + searchID + "'))" +
                "      AND friendship_status='E'" +
                "    )" +
                "";
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        insertFriends(rs);
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
            friendsListView.getItems().add("Keine Spieler gefunden!");
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
    private void addUsertoFriendlist(ActionEvent event) throws SQLException {

        if (friendsListView.getItems().contains("Keine Spieler gefunden!") || friendsListView.getItems().contains("null")) {

        } else {
            String uname = friendsListView.getSelectionModel().getSelectedItem();
            if (friendsListView.getItems().contains("Du und " + uname + " seit bereits die engsten Freunde!") || friendsListView.getItems().contains(uname + " ist noch nicht ganz mit dir Befreundet!") || friendsListView.getItems().contains(uname + " wurde als Freund hinzugefügt") || friendsListView.getItems().contains("Freundschaftsanfrage wurde bereits verschickt!") || friendsListView.getItems().contains(uname + " ist noch ausstehend")) {
            } else {

                String getUserId = "Select * from users where username = '" + uname + "'";
                System.out.println(getUserId);
                ResultSet getUserIdRs = statement.executeQuery(getUserId);
                if (getUserIdRs.next()) {
                    String uid = getUserIdRs.getString("USER_ID");
                    String searchInFriendship = "select * from friendship where user_two = '" + user.getUser_id() + "' and user_one = '" + uid + "' and FRIENDSHIP_STATUS = 'P'";
                    ResultSet searchInFriendshipRs = statement.executeQuery(searchInFriendship);
                    if (searchInFriendshipRs.next()) {
                        String updateToActive = "Update friendship set friendship_status ='E' where user_two = '" + user.getUser_id() + "' and user_one = '" + uid + "'";
                        statement.executeUpdate(updateToActive);
                        friendsListView.getItems().clear();
                        friendsListView.getItems().add(uname + " wurde als Freund hinzugefügt!");
                    } else {
                        String searchInFriendship2 = "select * from friendship where user_one = '" + user.getUser_id() + "' and user_two = '" + uid + "' UNION ALL select * from friendship where user_one = '" + user.getUser_id() + "' and user_two = '" + uid + "'";
                        ResultSet rs2 = statement.executeQuery(searchInFriendship2);
                        if (rs2.next()) {

                            System.out.println(rs2.getString("FRIENDSHIP_STATUS").toCharArray()[0] == 'E');

                            if(rs2.getString("FRIENDSHIP_STATUS").toCharArray()[0] == 'E') {
                                friendsListView.getItems().clear();
                                friendsListView.getItems().add("Du und " + uname + " seit bereits die engsten Freunde!");
                            } else if(rs2.getString("FRIENDSHIP_STATUS").toCharArray()[0] == 'P'){
                                friendsListView.getItems().clear();

                                if(rs2.getString("user_one").equals(userid.getText())){
                                    friendsListView.getItems().add(uname + " muss deine Anfrage akzeptieren.");
                                }else if(rs2.getString("user_two").equals(userid.getText())) {
                                    friendsListView.getItems().add(uname + " wartet noch auf deine Akzeptierung.");
                                }
                            }

                        } else {
                            String friendshipId = CreateCode.createIdCode("friendship", this.statement);
                            String insertIntoFriendship = "insert into friendship (friendship_id, user_one, user_two, friendship_status) values ('" + friendshipId + "', '" + user.getUser_id() + "', '" + uid + "', 'P')";
                            System.out.println(insertIntoFriendship);
                            statement.executeUpdate(insertIntoFriendship);
                            friendsListView.getItems().clear();
                            friendsListView.getItems().add("Freundschaftsanfrage wurde versendet!");
                        }
                    }
                }
            }
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
    private void playGame(ActionEvent event) throws IOException, URISyntaxException {
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
