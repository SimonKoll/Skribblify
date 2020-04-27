/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createLobby;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author simon
 */
public class CrobbyController implements Initializable {
    @FXML
    private Slider playersSlider;
    @FXML
    private Slider roundTimeSlider;
    @FXML
    private Slider roundsSlider;
    @FXML
    private Text playerCount;
    @FXML
    private Text roundTimeCount;
    @FXML
    private Text roundCount;
    @FXML
    private Button inviteBtn;
    @FXML
    private Button startBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void inviteFriends(ActionEvent event) {

    }

    @FXML
    private void startGame(ActionEvent event) {
    }
    
}
