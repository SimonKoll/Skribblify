/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createLobby;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import createCode.CreateCode;
import dialog.Dialog;
import dialog.Navigation;
import game_ui.client.GameC;
import game_ui.client.Point;
import game_ui.client.WebsocketClient;
import game_ui.client.WebsocketClientEndpoint;
import game_ui.server.util.ConsoleColor;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import login_registration.login.viewController.LoginC;
import login_registration.model.User;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import javax.imageio.ImageIO;
import javax.websocket.*;

@ClientEndpoint
public class CrobbyController  implements Initializable, Dialog {

    private Statement statement;
    private static User user;

    private static String gameCode = "";

    @FXML
    private Button inviteBtn;
    @FXML
    private Button startBtn;
    @FXML
    private ListView<String> lvFriends;
    @FXML
    private javafx.scene.control.TextField lobbyCodeInput;
    @FXML
    private Button joinGame;
    @FXML
    private Button btnLeave;

    private FXMLLoader loader;

    private Stage stage;

    @FXML
    private ListView<String> messageLV;

    ObservableList<String> chatMessages = FXCollections.observableArrayList();//create observablelist for listview


    @FXML
    private Button listBtn;


    private AnchorPane dialogVbox = new AnchorPane();
    private ListView lvGamesPublic = new ListView();
    final Stage dialog = new Stage();


    @FXML
    private Slider playerSlider;
    @FXML
    private Slider durationSlider;
    @FXML
    private Slider roundSlider;


    @FXML
    private Button btnCreate;

    public Session userSession = null;
    public CrobbyController crobbyController;
    private Stage stage1;
    private Scene dialogScene;

    private JSONObject games;


    private boolean isPublic = true;
    @FXML
    private Pane crobbyPane;
    @FXML
    private Pane gamePane;

    @FXML
    private TextField txtDrawer;


    public void intializeSocket(URI endpointURI) {
        try {
            ClientManager client = ClientManager.createClient();
            client.connectToServer(this, endpointURI);
            System.out.println("connection established...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CrobbyController() {

    }







    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject jso = new JSONObject(message);

        System.out.println(message);


        switch(jso.getString("type")) {
            case "join":
                System.out.println(ConsoleColor.SERVER + "NEW PLAYER JOINED.");
                Platform.runLater(() -> {
                    this.updateList(jso);
                    this.inviteBtn.setText(jso.getJSONObject("game").getString("gameID"));
                    gameCode = this.inviteBtn.getText();
                    this.toggleBtn.setDisable(true);
                    roundSlider.setValue(jso.getJSONObject("game").getInt("roundSize"));
                    durationSlider.setValue(jso.getJSONObject("game").getInt("roundDuration"));
                    this.joinGame.setDisable(true);

                });

                break;
            case "joinedGame":
                Platform.runLater(() -> {
                    inviteBtn.setText(jso.getJSONObject("game").getString("gameID"));
                    gameCode = this.inviteBtn.getText();
                    this.toggleBtn.setDisable(true);
                    roundSlider.setValue(jso.getJSONObject("game").getInt("roundSize"));
                    durationSlider.setValue(jso.getJSONObject("game").getInt("roundDuration"));
                });
                break;

            case "games":
                this.games = new JSONObject(message);
                break;
            case "leave":
                System.out.println(ConsoleColor.SERVER + "PLAYER LEFT THE GAME.");

                Platform.runLater(() -> {
                    this.updateList(jso);
                });

                break;


            case "start":
                this.switchScene();

                String newWord = "";
                String placeHolder = "";
                for (char c : jso.getString("word").toCharArray()) {
                    placeHolder += "_ ";
                    newWord += c + " ";
                }


                if(!this.user.getUsername().equals(jso.getJSONObject("drawer").getString("username"))) {
                    this.wordToGuess.setText(placeHolder);
                }else {
                    this.wordToGuess.setText(newWord);
                }

                this.txtDrawer.setText("Drawer:" + jso.getJSONObject("drawer").getString("username"));

                break;

            case "updateGame":

                if (jso.getBoolean("eraser")) {
                    g.clearRect(jso.getDouble("x"), jso.getDouble("y"), jso.getDouble("size"), jso.getDouble("size"));
                } else {
                    g.setFill(Paint.valueOf(jso.getString("color")));
                    if (jso.getBoolean("bucket")) {
                        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    } else {
                        g.fillOval(jso.getDouble("x"), jso.getDouble("y"), jso.getDouble("size"), jso.getDouble("size"));
                    }
                }
                break;

            case "chat":
                Platform.runLater(() -> {
                    chatMessages.add(jso.getString("username") + " - " + jso.getString("message"));
                });
                break;

            case "status":
                switch(jso.getInt("code")) {
                    case 27092002:
                        Platform.runLater(() -> {
                         lvFriends.getItems().clear();
                         lobbyCodeInput.clear();
                         this.startBtn.setDisable(true);
                         this.btnLeave.setDisable(true);
                         this.listBtn.setDisable(false);
                         this.btnCreate.setDisable(false);
                         this.toggleBtn.setDisable(false);

                         playerSlider.setDisable(false);
                         durationSlider.setDisable(false);
                         roundSlider.setDisable(false);

                        });
                        System.out.println(ConsoleColor.PURPLE + "Sie haben das Spiel verlassen!");

                        break;


                    case 199:
                        System.out.println(ConsoleColor.SERVER + "Sie haben eine Lobby erstellt!");
                        Platform.runLater(() -> {
                            this.listBtn.setDisable(true);
                            this.btnLeave.setDisable(false);
                            this.btnCreate.setDisable(true);
                        });
                        break;

                    case 24022003:
                        System.out.println(ConsoleColor.SERVER + "Sie sind einer Lobby beigetretten. ");
                        Platform.runLater(() -> {
                            this.startBtn.setDisable(true);
                            playerSlider.setDisable(true);
                            durationSlider.setDisable(true);
                            roundSlider.setDisable(true);
                            this.btnLeave.setDisable(false);
                            this.btnCreate.setDisable(true);
                            this.listBtn.setDisable(true);
                        });
                        break;

                    case 155:
                        Platform.runLater(() -> {
                            chatMessages.add(jso.getString("message"));
                        });
                        break;
                }
                break;
        }

    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);

    }



    public void updateList(JSONObject jso) {
        playerSlider.setValue(jso.getJSONObject("game").getJSONArray("players").length());
        playersLabel.setText(jso.getJSONObject("game").getJSONArray("players").length() + "/" + jso.getJSONObject("game").getInt("maxPlayers") + " Player(s) joined!");


        System.out.println();
        boolean owner = false;

        for(Object o : jso.getJSONObject("game").getJSONArray("players")) {
            JSONObject player = new JSONObject(o.toString());

            if(player.getString("username").trim().equals(user.getUsername().trim())) {
                if(player.getString("sessionID").equals(jso.getJSONObject("game").getString("hostID"))) {
                    owner = true;
                }
            }
        }


        boolean ownerHere = false;

        for(Object o : jso.getJSONObject("game").getJSONArray("players")) {
            JSONObject player = new JSONObject(o.toString());

            if(player.getString("sessionID").trim().equals(jso.getJSONObject("game").getString("hostID"))) {
                ownerHere = true;
            }
        }



        if(jso.getJSONObject("game").getJSONArray("players").length() >= 2 && owner) {
            this.startBtn.setDisable(false);
        }else {
            this.startBtn.setDisable(true);
        }


        if(!ownerHere && jso.getJSONObject("game").getJSONArray("players").length() >= 2) {
            this.startBtn.setDisable(false);
        }



        lvFriends.getItems().clear();
        for(Object o : jso.getJSONObject("game").getJSONArray("players")) {
            String username = new JSONObject(o.toString()).getString("username");
            if(new JSONObject((o.toString())).getString("sessionID").equals(jso.getJSONObject("game").getString("hostID"))){
                lvFriends.getItems().add(username + " [Admin]");
            }else {
                lvFriends.getItems().add(username);
            }
        }
    }



    public void show(Stage stage, Statement statement, User user) {


        CrobbyController.user = user;


        try {
            loader = new FXMLLoader(LoginC.class.getClassLoader().getResource("createLobby/CrobbyV.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);


            if (stage == null) {
                stage = new Stage();
            }

            this.stage = stage;


            this.stage.setScene(scene);
            stage.setTitle("Skribblify - Lobby");

            stage1 = stage;

            crobbyController = loader.getController();
            crobbyController.intializeSocket(new URI("ws://localhost:8025/websockets/draw"));
            crobbyController.statement = statement;
            crobbyController.init();
            crobbyController.connectServer();
            crobbyController.initializeCanvas();
            crobbyController.initializeChat();


            crobbyController.crobbyPane.setDisable(false);
            crobbyController.crobbyPane.setVisible(true);


            crobbyController.gamePane.setDisable(true);
            crobbyController.gamePane.setVisible(false);


            stage.show();



            stage.setOnCloseRequest(event -> {
                System.out.println(gameCode);
                crobbyController.sendMessage("{\"type\": \"leaveGame\", \"lobbyID\": '" + gameCode + "'}");

            });



        } catch (IOException | SQLException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with CreateLobby.fxml!");
            ex.printStackTrace(System.err);
            System.exit(1);

        }
    }





    public void joinNewLobby() throws SQLException, InterruptedException {
        String createCode = CreateCode.createIdCode("lobby", this.statement);
        this.inviteBtn.setText(createCode);
        gameCode = this.inviteBtn.getText();
        this.sendMessage("{\"type\": \"createGame\", \"visibility\":" + this.isPublic + ", \"roundDuration\":" + (int) durationSlider.getValue() +  ", \"roundSize\":" + (int) roundSlider.getValue() + ", \"lobbyID\": '" + inviteBtn.getText().replaceAll(" ", "-") + "', \"maxPlayers\":" +  (int) playerSlider.getValue() + "}");
    }

    @FXML
    private void inviteFriends(ActionEvent event) {
    }






    @FXML
    private void copyClip(MouseEvent event) {
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(new StringSelection(inviteBtn.getText()), null);
    }

    @FXML
    private void joinLobby(MouseEvent event) {
        if(this.lobbyCodeInput.getText().trim().length() > 0) {
            this.sendMessage("{\"type\": \"joinLobby\", \"lobbyID\": '" + this.lobbyCodeInput.getText() + "'}");

            Platform.runLater(() -> {
                this.lobbyCodeInput.setText(this.lobbyCodeInput.getText());
            });

        }else{
            System.out.println("Sie müssen einen gültigen Code angeben.");
        }
    }

    public void connectServer() throws InterruptedException, SQLException {
        this.sendMessage("{\"type\": \"login\", \"username\": '" + CrobbyController.user.getUsername() + "'}");
        Thread.sleep(1000);
        this.sendMessage("{\"type\": \"getGames\"}");

    }



    @FXML
    private void leaveLobby(MouseEvent event) {
        if(!btnLeave.isDisabled()) {
            System.out.println(this.inviteBtn.getText().trim());
            this.sendMessage("{\"type\": \"leaveGame\", \"lobbyID\": '" + this.inviteBtn.getText().trim() + "'}");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage1);
        dialogVbox.getChildren().add(lvGamesPublic);
        dialogScene = new Scene(dialogVbox, 500, 500);

        lvGamesPublic.setMinWidth(500);
        lvGamesPublic.setMinHeight(500);


        this.btnLeave.setDisable(true);
        this.startBtn.setDisable(true);




    }








    @FXML
    private Text playersLabel;
    @FXML
    private Text durationLabel;
    @FXML
    private Text roundsLabel;
    @FXML
    private Text Players;

    @FXML
    private ToggleButton toggleBtn;

    private boolean isGameCreated = false;

    private final String PUBLIC = "Public";
    private final String PRIVATE = "Private";





    private void init() throws SQLException {


        playerSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            playerSlider.setValue((int) Math.round(newVal.doubleValue()));
            newVal = newVal.intValue();
            playersLabel.setText(newVal + " Player(s)");
        });

        durationSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            durationSlider.setValue((int) Math.round(newVal.doubleValue()));
            newVal = newVal.intValue();
            durationLabel.setText(newVal + " Seconds");
        });

        roundSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            roundSlider.setValue((int) Math.round(newVal.doubleValue()));
            newVal = newVal.intValue();
            roundsLabel.setText(newVal + " Round(s)");
        });
    }



    @FXML
    private void startGame(ActionEvent event) throws IOException {
        this.sendMessage("{\"type\": \"startGame\"}");
    }




    @FXML
    private void createGame(MouseEvent event) {

        try {
            this.joinNewLobby();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        isGameCreated = true;
        playerSlider.setDisable(true);
        roundSlider.setDisable(true);
        durationSlider.setDisable(true);


        durationSlider.valueProperty().removeListener((obs, oldval, newVal) -> {
            durationSlider.setValue((int) Math.round(newVal.doubleValue()));
            newVal = newVal.intValue();
            durationLabel.setText(newVal + " Seconds");
        });
        roundSlider.valueProperty().removeListener((obs, oldval, newVal) -> {
            roundSlider.setValue((int) Math.round(newVal.doubleValue()));
            newVal = newVal.intValue();
            roundsLabel.setText(newVal + " Seconds");
        });
        playersLabel.setText(lvFriends.getItems().size() + " Player(s) joined");
        playerSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            playersLabel.setText(lvFriends.getItems().size() + " Player(s) joined");
        });
    }




    @FXML
    public void openWindow(ActionEvent event) {

        this.sendMessage("{\"type\": \"getGames\"}");
        this.lvGamesPublic.getItems().clear();

        if(this.games == null || this.games.getJSONArray("games").length() == 0){
            lvGamesPublic.getItems().add("No public games available.");
        }else{
            for(Object o : this.games.getJSONArray("games")) {
                JSONObject game = new JSONObject(o.toString());
                boolean creatorHere = false;
                String creatorString = "";
                for(Object p : game.getJSONArray("players")) {
                    System.out.println(ConsoleColor.BLUE + game + "\n" + ConsoleColor.reset());
                    JSONObject player = new JSONObject(p.toString());

                    creatorString = game.getString("gameID") + "-" + player.getString("username") + "  (Creator left.)";

                    if(player.getString("sessionID").equals(game.getString("hostID")) && player.has("username")) {
                        creatorHere = true;
                        creatorString = game.getString("gameID") + "-" + player.getString("username");
                    }
                }
                lvGamesPublic.getItems().add(creatorString);



            }
        }





        lvGamesPublic.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                System.out.println(lvGamesPublic.getSelectionModel()
                        .getSelectedItem());

                //join lobby

                System.out.println(lvGamesPublic.getSelectionModel().getSelectedItem().toString().split("-")[0]);
                this.sendMessage("{\"type\": \"joinLobby\", \"lobbyID\": '" + lvGamesPublic.getSelectionModel().getSelectedItem().toString().split("-")[0] + "'}");

                Platform.runLater(() -> {
                    this.lobbyCodeInput.setText(this.lobbyCodeInput.getText());
                });
                final Node source = (Node) click.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                dialog.close();
            }
        });
        dialog.setScene(dialogScene);
        dialog.show();    }

    @FXML
    public void toggleBtnToggled(ActionEvent event) {
        if (toggleBtn.isSelected()) {
            toggleBtn.setText("Public");
            this.isPublic = true;
        } else {
            toggleBtn.setText("Private");
            this.isPublic = false;
        }
    }




    /* GAMEC */

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
        private LinkedList<game_ui.client.Point> layerLA = new LinkedList<>();
        private LinkedList<game_ui.client.Point> layerHistory = new LinkedList<>();
        private LinkedList<game_ui.client.Point> layerChain = new LinkedList<>();




        public void countDown() {
            if((progressBar.getProgress() - step) >= 0){
                progressBar.setProgress(progressBar.getProgress() - step);
                timeLeft.setText(String.valueOf(Math.round(progressBar.getProgress() *  100 )));
            }else{
                roundEnd = true;
                chooseWord.setVisible(true);
            }
        }

        public void initializeCanvas() {
           


            progressBar.setProgress(1);
            chooseWord.setVisible(false);
            colorPicker.setValue(Color.BLACK);
            g = canvas.getGraphicsContext2D();
            canvas.setOnMouseMoved(e -> {
            });

            canvas.setOnMouseClicked(e -> {
                if(!roundEnd && CrobbyController.user.getUsername().equals(txtDrawer.getText().split(":")[1])){
                    addDrawHistory(e);
                }
            });

            canvas.setOnMouseDragged(e -> {
                if(!roundEnd && CrobbyController.user.getUsername().equals(txtDrawer.getText().split(":")[1])){
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
            game_ui.client.Point p = new game_ui.client.Point(eraser.isSelected(), bucket.isSelected(), e.getX(), e.getY(), brushSize.getValue(), colorPicker.getValue());



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
                game_ui.client.Point p = (Point) element;

                double size = p.getSize();
                double x = p.getX() - size / 2;
                double y = p.getY() - size / 2;


                this.sendMessage("{\"type\": \"updateGame\", \"game\": '" + this.inviteBtn.getText() + "', \"x\": " + x + ", \"y\": " + y + ", \"bucket\":" + bucket.isSelected() +", \"eraser\": " + eraser.isSelected() + " , \"color\":" + p.getColor() + ", \"size\": " + size + "}");


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



        public void switchScene() {
            if(this.gamePane.isDisabled() && !this.gamePane.isVisible()){
                this.crobbyPane.setVisible(false);
                this.crobbyPane.setDisable(true);

                this.gamePane.setVisible(true);
                this.gamePane.setDisable(false);
            }
        }


    public void initializeChat() {
        messageLV.setItems(chatMessages);//attach the observablelist to the listview

        guessInputField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (guessInputField.getText().isEmpty()) {
                } else {
                    this.sendMessage("{\"type\": \"chat\", \"sender\": '" + this.userSession.getId() + "', \"message\": '" + guessInputField.getText() + "'}");
                    guessInputField.setText("");//clear 1st user's textfield

                }
            }
        });
    }

    @FXML
    private void eraserSelected(MouseEvent event) {
            if(bucket.isSelected()) {
                bucket.setSelected(false);
            }
    }

    @FXML
    private void fillSelected(MouseEvent event) {
            if(eraser.isSelected()) {
                eraser.setSelected(false);
            }
    }
    }


