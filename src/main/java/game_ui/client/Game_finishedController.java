/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_ui.client;

import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

import dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_registration.model.User;

/**
 * FXML Controller class
 *
 * @author simon
 */
public class Game_finishedController implements Initializable, Dialog {
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
    private Text players;
    @FXML
    private Button homeBtn;
    @FXML
    private Text secondPoints;
    @FXML
    private Text firstPoints;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToDashboard(ActionEvent event) {
    }

    @Override
    public void show(Stage stage, Statement statement, User user) {

    }
}
