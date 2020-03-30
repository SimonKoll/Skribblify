package game_ui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

public class PaintController {
    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider brushSize;

    @FXML
    private CheckBox eraser;

    public void initialize(){
        GraphicsContext g = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(e -> {
            double size = brushSize.getValue();
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if(eraser.isSelected()){
                g.clearRect(x, y, size, size);
            }else{
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }
        });
    }

    public void onSave(){
        try{
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        }catch(Exception e){
            System.out.println("Failed to save image: " + e);
        }
    }

    public void onExit(){
        Platform.exit();
    }
}
