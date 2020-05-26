package game_ui.client;


import dialog.Dialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_registration.login.viewController.LoginC;
import login_registration.model.User;

public class GameC implements Dialog {
    @FXML
    private Canvas canvas;

    private GraphicsContext g;


    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider brushSize;

    @FXML
    private CheckBox eraser;

    @FXML
    private CheckBox bucket;
    @FXML
    private TextField guessInputField;
    @FXML
    private Pane guesses;
    @FXML
    private TextField wordToGuess;
    @FXML
    private Text timeLeft;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Pane chooseWord;
    @FXML
    private javafx.scene.control.Button option1;
    @FXML
    private javafx.scene.control.Button option2;
    @FXML
    private javafx.scene.control.Button option3;

    private double step = 0.0001;
    private boolean roundEnd = false;
    private Statement statement;



    public  void show(Stage stage, Statement statement, User user) {
        KeyCombination cz = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);


        try {
            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("game_ui/GameV.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);
            scene.getAccelerators().put(cz, rn);
            if(stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Game");


            GameC gameC = (GameC) loader.getController();
            gameC.initialize();
            gameC.statement = statement;

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with GameV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }


    public void countDown() {
        if((progressBar.getProgress() - step) >= 0){
            progressBar.setProgress(progressBar.getProgress() - step);
            timeLeft.setText(String.valueOf(Math.round(progressBar.getProgress() *  100 )));
        }else{
            roundEnd = true;
            chooseWord.setVisible(true);
        }
    }

    public void initialize() {
        progressBar.setProgress(1);
        chooseWord.setVisible(false);

        colorPicker.setValue(Color.BLACK);

        g = canvas.getGraphicsContext2D();
        canvas.setOnMouseMoved(e -> {
        });

        canvas.setOnMouseClicked(e -> {
            if(!roundEnd){
                drawPoint(e.getX(), e.getY(), g);
                countDown();
            }
        });

        canvas.setOnMouseDragged(e -> {
            if(!roundEnd){
                drawPoint(e.getX(), e.getY(), g);
                countDown();
            }
        });

    }

    @FXML
    public void onSave(){
        try{
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        }catch(Exception e){
            System.out.println("Failed to save image: " + e);
        }
    }


    public void drawPoint(double x1, double y1, GraphicsContext g){
        double size = brushSize.getValue();
        double x = x1 - size / 2;
        double y = y1 - size / 2;

        if(eraser.isSelected()){
            g.clearRect(x, y, size, size);
        }else{
            g.setFill(colorPicker.getValue());

            if(bucket.isSelected()){
                g.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            }else{
                g.fillOval(x, y, size, size);
            }
        }
    }



    @FXML
    private void option1Ready(ActionEvent event) {
        System.out.println(option1.getText());
        nextRound(option1.getText());
    }

    @FXML
    private void option2Ready(ActionEvent event) {
        System.out.println(option2.getText());
        nextRound(option2.getText());
    }

    @FXML
    private void option3Ready(ActionEvent event) {
        System.out.println(option3.getText());
        nextRound(option3.getText());
    }

    public void nextRound(String newWord) {
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        wordToGuess.setText(newWord);
        progressBar.setProgress(1);
        chooseWord.setVisible(false);
        roundEnd = false;
    }

    Runnable rn = ()-> {
        System.out.println("working,...");
    };




}
