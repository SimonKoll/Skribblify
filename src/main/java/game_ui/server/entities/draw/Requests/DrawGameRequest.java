package game_ui.server.entities.draw.Requests;

public class DrawGameRequest extends DrawRequest{
    private String gameID;
    private double x,y;
    private String mode;
    private String color;

    public DrawGameRequest(String type) {
        super(type);
    }

    public DrawGameRequest(String type, String gameID) {
        super(type);
        this.gameID = gameID;
    }

    public DrawGameRequest(String type, String gameID, double x, double y, String mode, String color) {
        super(type);
        this.gameID = gameID;
        this.x = x;
        this.y = y;
        this.mode = mode;
        this.color = color;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
