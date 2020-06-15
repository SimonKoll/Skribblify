package game_ui.client;


import dialog.Dialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
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
    @FXML
    private Button knopf;

    private double step = 0.01;
    private boolean roundEnd = false;
    private LinkedList<Point> layerLA = new LinkedList<>();
    private LinkedList<Point> layerHistory = new LinkedList<>();
    private LinkedList<Point> layerChain = new LinkedList<>();
    private User user;


    public  void show(Stage stage, Statement statement, User user) {


        this.user = user;

        try {
            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("game_ui/GameV.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            if(stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Game");
            GameC gameC = loader.getController();
            gameC.initialize();
            this.initializeSockets();
            stage.show();


        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with GameV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private void initializeSockets() throws URISyntaxException {
       // final WebsocketClientEndpoint clientGameEndpoint = new WebsocketClientEndpoint(new URI("ws://localhost:8025/websockets/skribbl"));
        //clientGameEndpoint.sendMessage("{\"type\": \"login\", \"username\": " + this.user.getUsername() + "}");
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
                addDrawHistory(e);
            }
        });

        canvas.setOnMouseDragged(e -> {
            if(!roundEnd){
                addDrawHistory(e);
           }
       });
    }

    @FXML
    private void undo(){
        if(!layerHistory.isEmpty()) {
            layerHistory.removeLast();
        }
        drawPoint(false);
        layerLA.clear();
    }




    private void addDrawHistory(MouseEvent e) {
        Point p = new Point(eraser.isSelected(), bucket.isSelected(), e.getX(), e.getY(), brushSize.getValue(), colorPicker.getValue());



        layerLA.clear();
        layerLA.add(p);
        if(e.isDragDetect()){
            layerChain.add(p);
        }else{
            layerHistory.addAll(layerLA);
        }


        drawPoint(true);


        countDown();
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
    public void drawPoint(boolean lastAction){
        LinkedList<Point> layer = new LinkedList<>();
            if(lastAction){
                layer = layerLA;

            }else{
                 layer = layerHistory;
                 g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }



            for(Object element : layer) {
                Point p = (Point) element;

                double size = p.getSize();
                double x = p.getX() - size / 2;
                double y = p.getY() - size / 2;

                if (p.isEraser()) {
                    g.clearRect(x, y, size, size);
                } else {
                    g.setFill(p.getColor());

                    if (bucket.isSelected()) {
                        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    } else {
                        g.fillOval(x, y, size, size);
                    }
                }
            }

    }

    @FXML
    private void option1Ready(ActionEvent event) {
        nextRound(option1.getText());
    }

    @FXML
    private void option2Ready(ActionEvent event) {
        nextRound(option2.getText());
    }

    @FXML
    private void option3Ready(ActionEvent event) {
        nextRound(option3.getText());
    }

    public void nextRound(String newWord) {
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        wordToGuess.setText(newWord);
        progressBar.setProgress(1);
        chooseWord.setVisible(false);
        roundEnd = false;
    }
}
