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
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
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

    @FXML
    private CheckBox bucket;

    public void initialize(){
        System.out.println("initialazing");
        colorPicker.setValue(Color.BLACK);
        GraphicsContext g = canvas.getGraphicsContext2D();

        canvas.setOnMouseMoved(e -> {
        });

        canvas.setOnMouseClicked(e -> {
            drawPoint(e.getX(), e.getY(), g);
        });

        canvas.setOnMouseDragged(e -> {
            System.out.println("mouse is dragged...");
            drawPoint(e.getX(), e.getY(), g);
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
        System.out.println("drawing point...");
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

}
