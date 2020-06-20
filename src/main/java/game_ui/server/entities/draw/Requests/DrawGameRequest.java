package game_ui.server.entities.draw.Requests;

public class DrawGameRequest extends DrawRequest{
    private String gameID;
    private double x,y;
    private boolean bucket;
    private boolean eraser;
    private String color;
    private double size;

    public DrawGameRequest(String type) {
        super(type);
    }

    public DrawGameRequest(String type, String gameID) {
        super(type);
        this.gameID = gameID;
    }

    public DrawGameRequest(String type, String gameID, double x, double y, boolean bucket, boolean eraser, String color, double size) {
        super(type);
        this.gameID = gameID;
        this.x = x;
        this.y = y;
        this.bucket = bucket;
        this.eraser = eraser;

        this.color = color;
        this.size = size;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "DrawGameRequest{" +
                "gameID='" + gameID + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", bucket=" + bucket +
                ", eraser=" + eraser +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }

    public boolean isEraser() {
        return eraser;
    }

    public void setEraser(boolean eraser) {
        this.eraser = eraser;
    }

    public boolean isBucket() {
        return bucket;
    }

    public void setBucket(boolean bucket) {
        this.bucket = bucket;
    }
}
