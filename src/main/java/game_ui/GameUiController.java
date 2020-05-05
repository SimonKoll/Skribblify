package game_ui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameUiController {
    @FXML
    private Canvas canvas;

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

    private double step = 0.01;
    private boolean roundEnd = false;

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
        GraphicsContext g = canvas.getGraphicsContext2D();

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
        wordToGuess.setText(newWord);
        progressBar.setProgress(1);
        chooseWord.setVisible(false);
        roundEnd = false;
    }

}
