package dashboard.viewController;

import createLobby.CrobbyController;
import dashboard.model.Dashboard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import login_registration.model.User;
import login_registration.login.viewController.LoginC;

import java.io.IOException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardC {


    private Statement statement;
    private Dashboard model;

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
    private  Text username;
    @FXML
    private  Text userid;
    @FXML
    private Button playBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button playBtn1;

    @FXML
    private HBox charts;

    private User user;

    public static void show(Stage stage, Statement statement, User user) {



        // Animation test
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

            dashboardC.model = new Dashboard();
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with DashboardV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }


    private void init(User user){
        this.user = user;
        if(user != null) {
            System.out.println(this.user.toString());
            userid.setText(this.user.getUser_id());
            username.setText(this.user.getUsername());
        }

        drawChart();
    }

    private void drawChart(){
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
    private void showAllPlayers(ActionEvent event) {
    }

    @FXML
    private void addUsertoFriendlist(ActionEvent event) {
    }

    @FXML
    private void showOnlinePlayers(ActionEvent event) {
    }

    @FXML
    private void showOfflinePlayers(ActionEvent event) {
    }

    @FXML
    private void showPendingPlayers(ActionEvent event) {
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        this.user = null;
        Stage stage;
        Parent root;


        stage = (Stage) logoutBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/login_registration/login/LoginV.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        LoginC.show(stage, this.statement);
    }

    @FXML
    private void playGame(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;


        stage = (Stage) playBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/createLobby/Crobby.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        CrobbyController.show(stage, this.statement);
    }

}
