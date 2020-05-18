package dashboard.viewController;

import createLobby.CrobbyController;
import dashboard.model.Dashboard;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.client.GameC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardC implements Dialog {


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
    private HBox charts;

    private User user;

    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Pane scrollPane;

    public  void show(Stage stage, Statement statement, User user) {
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
        scrollBar.setMax(540);
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                scrollPane.setLayoutY(-new_val.doubleValue());
            }
        });
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
        Navigation.navigate(logoutBtn, "/login_registration/login/LoginV.fxml", this.statement, null, new LoginC());
    }

    @FXML
    private void playGame(ActionEvent event) throws IOException {
        Navigation.navigate(playBtn, "/createLobby/CrobbyV.fxml", this.statement, this.user, new CrobbyController());
    }

    public void smoothScrolling(int scrollPoint) throws InterruptedException {
        int start = (int)scrollPane.getLayoutY();
        int diff = (int) Math.round(scrollPane.getLayoutY() - scrollPoint);


        for (int i = 0; i < Math.abs(diff); i++) {
            scrollPane.setLayoutY(i + start);
            Thread.sleep(500);
        }
    }



}
