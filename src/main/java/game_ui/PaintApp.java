package game_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaintApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(PaintApp.class.getClassLoader().getResource("game_ui.fxml"))));
        stage.setTitle("Paint App");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
