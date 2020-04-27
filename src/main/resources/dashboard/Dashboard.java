/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Matthias
 */
public class Dashboard implements Initializable {

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
    private Button statsBtn;
    @FXML
    private Pane chartStats;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void logout(ActionEvent event) {
    }
    
}
