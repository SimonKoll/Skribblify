import com.google.api.services.sheets.v4.SheetsScopes;
        import fonts.SheetsQuickstart;
        import javafx.application.Application;

        import javafx.scene.Scene;
        import javafx.scene.text.Text;
        import javafx.scene.text.TextFlow;
        import javafx.stage.Stage;

public class Main extends Application {
    private static SheetsQuickstart SheetsQuickStart;
    @Override
    public void start(Stage primaryStage) throws Exception {

        SheetsQuickstart.loadConfiguration(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
