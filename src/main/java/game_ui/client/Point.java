package game_ui.client;

import javafx.scene.paint.Color;

public class Point {
    private boolean eraser;
    private boolean bucket;
    private double x,y;
    private double size;
    private Color color;

    public Point(boolean eraser, boolean bucket, double x, double y, double size, Color color) {
        this.eraser = eraser;
        this.bucket = bucket;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public boolean isEraser() {
        return eraser;
    }



    public boolean isBucket() {
        return bucket;
    }

    public double getX() {
        return x;
    }



    public double getY() {
        return y;
    }



    public double getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Point{" +
                "eraser=" + eraser +
                ", bucket=" + bucket +
                ", x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", color=" + color +
                '}';
    }
}
