package game_ui.client;


import dialog.Dialog;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    private double step = 0.0001;
    private boolean roundEnd = false;
    private LinkedList<Point> layerLA = new LinkedList<>();
    private LinkedList<Point> layerHistory = new LinkedList<>();
    private LinkedList<Point> layerChain = new LinkedList<>();
    @FXML
    private ListView<String> messageLV;
    @FXML
    private ListView<?> userLv;

    ObservableList<String> chatMessages = FXCollections.observableArrayList();//create observablelist for listview

    public void show(Stage stage, Statement statement, User user) {

        try {
            FXMLLoader loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("game_ui/GameV.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            if (stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("Skribblify - Game");

            GameC gameC = loader.getController();
            gameC.initialize();
            stage.show();


        } catch (IOException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with GameV.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }

    public void countDown() {
        /*
        if((progressBar.getProgress() - step) >= 0){
            progressBar.setProgress(progressBar.getProgress() - step);
            timeLeft.setText(String.valueOf(Math.round(progressBar.getProgress() *  100 )));
        }else{
            roundEnd = true;
            chooseWord.setVisible(true);;
        }
);*/
        IntegerProperty seconds = new SimpleIntegerProperty();
        progressBar.progressProperty().bind(seconds.divide(60.0));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 60)),
                new KeyFrame(Duration.minutes(1), e -> {
                    timeLeft.setText(String.valueOf(seconds));
                    System.out.println("Minute over");
                }, new KeyValue(seconds, 0))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void initialize() {
        messageLV.setItems(chatMessages);//attach the observablelist to the listview
        guessInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (guessInputField.getText().isEmpty()) {
                    } else {
                        chatMessages.add(guessInputField.getText());//get 1st user's text from his/her textfield and add message to observablelist
                        guessInputField.setText("");//clear 1st user's textfield
                    }
                }
            }
        });
        progressBar.setProgress(1);
        chooseWord.setVisible(false);

        colorPicker.setValue(Color.BLACK);

        g = canvas.getGraphicsContext2D();
        canvas.setOnMouseMoved(e -> {
        });

        canvas.setOnMouseClicked(e -> {
            if (!roundEnd) {
                addDrawHistory(e);
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (!roundEnd) {
                addDrawHistory(e);
            }
        });


    }

    @FXML
    private void undo() {


        if (!layerHistory.isEmpty()) {
            layerHistory.removeLast();
        }
        drawPoint(false);


        layerLA.clear();


    }


    private void addDrawHistory(MouseEvent e) {
        Point p = new Point(eraser.isSelected(), bucket.isSelected(), e.getX(), e.getY(), brushSize.getValue(), colorPicker.getValue());


        layerLA.clear();
        layerLA.add(p);
        if (e.isDragDetect()) {
            layerChain.add(p);
        } else {
            layerHistory.addAll(layerLA);
        }


        drawPoint(true);


        countDown();
    }

    @FXML
    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void drawPoint(boolean lastAction) {
        LinkedList<Point> layer = new LinkedList<>();
        if (lastAction) {
            layer = layerLA;

        } else {
            layer = layerHistory;
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        System.out.println("LA: " + layerLA);
        System.out.println("HIS: " + layerHistory);

        for (Object element : layer) {
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
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        wordToGuess.setText(newWord);
        progressBar.setProgress(1);
        chooseWord.setVisible(false);
        roundEnd = false;
    }
}
