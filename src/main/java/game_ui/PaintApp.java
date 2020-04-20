package game_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaintApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene myScene = new Scene(FXMLLoader.load(PaintApp.class.getClassLoader().getResource("game_ui/game_ui.fxml")));
        myScene.getStylesheets().add(PaintApp.class.getClassLoader().getResource("style/game_ui.css").toExternalForm());

        stage.setScene(myScene);

        stage.setTitle("Paint App");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
