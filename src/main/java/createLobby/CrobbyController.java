/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createLobby;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import createCode.CreateCode;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.client.GameC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import login_registration.login.viewController.LoginC;
import login_registration.model.User;

/**
 * FXML Controller class
 *
 * @author simon
 */
public class CrobbyController implements Initializable, Dialog {

    private static Stage stage;
    private Statement statement;
    private static User user;

    @FXML
    private Button inviteBtn;
    @FXML
    private Button startBtn;
    @FXML
    private ListView<String> lvFriends;

    public void show(Stage stage, Statement statement, User user) {
        CrobbyController.user = user;

        try {
            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("createLobby/CrobbyV.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }


            stage.setScene(scene);
            stage.setTitle("Skribblify - Lobby");


            CrobbyController crobbyController = loader.getController();
            crobbyController.init();

            crobbyController.statement = statement;
            CrobbyController.stage = stage;

            stage.show();

        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with CreateLobby.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }
    @FXML
    private Slider playerSlider;
    @FXML
    private Slider durationSlider;
    @FXML
    private Slider roundSlider;

    private void init() throws SQLException {
        System.out.println("tesing....");
        String gameCode = CreateCode.createIdCode("lobby",this.statement);
        inviteBtn.setText(gameCode);


        lvFriends.getItems().add(this.user.getUsername());

        playerSlider.valueProperty().addListener((obs, oldval, newVal) ->
                playerSlider.setValue((int) Math.round(newVal.doubleValue())));

        durationSlider.valueProperty().addListener((obs, oldval, newVal) ->
                durationSlider.setValue((int) Math.round(newVal.doubleValue())));

        roundSlider.valueProperty().addListener((obs, oldval, newVal) ->
                roundSlider.setValue((int) Math.round(newVal.doubleValue())));
    }

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
    private void startGame(ActionEvent event) throws IOException {
        Navigation.navigate(startBtn, "/game_ui/GameV.fxml", this.statement, CrobbyController.user, new GameC());
    }

    @FXML
    private void copyClip(MouseEvent event) {
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(new StringSelection(inviteBtn.getText()), null);
    }
}
