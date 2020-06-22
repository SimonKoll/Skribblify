package placement;

import dashboard.viewController.DashboardC;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.server.util.ConsoleColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_registration.login.viewController.LoginC;
import login_registration.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlacementC implements Initializable, Dialog {
    @FXML
    private Text secondUsername;
    @FXML
    private Text firstUsername;
    @FXML
    private Text thirdUsername;
    @FXML
    private Text thirdPoints;
    @FXML
    private Text place;

    @FXML
    private Text yourPoints;

    @FXML
    private Button homeBtn;
    @FXML
    private Text secondPoints;
    @FXML
    private Text firstPoints;
    private Statement statement;
    private User user;
    @FXML
    private AnchorPane apSecond;
    @FXML
    private AnchorPane apFirst;
    @FXML
    private AnchorPane apThird;





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apFirst.setVisible(false);
        apSecond.setVisible(false);
        apThird.setVisible(false);
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        try {
            Navigation.navigate(homeBtn, "/dashboard/DashboardV.fxml", this.statement, user, new DashboardC());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public  void showPlacement(Stage stage, Statement statement, User user, JSONObject jso) {




        try {


            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("placement/PlacementV.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Placement");



            PlacementC placementC = (PlacementC) loader.getController();
            placementC.statement = statement;
            placementC.user = user;

            placementC.insertEntries(jso);

            stage.show();

        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with Placement.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }


    @Override
    public void show(Stage stage, Statement statement, User user) {

    }


    private void insertEntries(JSONObject jso) throws SQLException {

        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();


        for (int i = 0; i < jso.getJSONObject("game").getJSONArray("players").length(); i++) {
            jsonValues.add(jso.getJSONObject("game").getJSONArray("players").getJSONObject(i));
        }

        jsonValues.sort(new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "points";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                int valA = 0, valB = 0;

                try {
                    valA = (int) a.get(KEY_NAME);
                    valB = (int) b.get(KEY_NAME);
                } catch (JSONException e) {
                    //do something
                }

                return valB - valA;
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jso.getJSONObject("game").getJSONArray("players").length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }




            int index = 0;
            for(Object o : sortedJsonArray) {
                JSONObject player = new JSONObject(o.toString());

                if(index == 0) {
                    apFirst.setVisible(true);
                    firstUsername.setText(player.getString("username"));
                    firstPoints.setText("" + player.getInt("points") + " Punkte");
                }

                if(index == 1) {
                    apSecond.setVisible(true);

                    secondUsername.setText(player.getString("username"));
                    secondPoints.setText("" + player.getInt("points") + " Punkte");
                }

                if(index == 2) {


                    apThird.setVisible(true);

                    thirdUsername.setText(player.getString("username"));
                    thirdPoints.setText("" + player.getInt("points") + " Punkte");
                }


                if(player.getString("username").equals(user.getUsername())) {
                    place.setText("" + (index + 1) + "/" + sortedJsonArray.length());
                    yourPoints.setText(player.getInt("points") + " Points");

                    String query = "INSERT INTO USERSTATS (user_id, lobby_id, score, placement)VALUES ('" + user.getUser_id() + "', '" + jso.getJSONObject("game").getString("gameID") + "', " + player.getInt("points")+ ", " + (index + 1) + ")";
                    statement.executeUpdate(query);



                }


                index ++;
            }



        System.out.println(ConsoleColor.SERVER + sortedJsonArray);
    }

}
