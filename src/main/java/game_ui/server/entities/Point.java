package game_ui.server.entities;

public class Point {


    private SkribblPlayer owner;
    private Number color;
    private int x, y;

    public Point(SkribblPlayer owner, Number color, int x, int y){
        this.owner = owner;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public SkribblPlayer getOwner() {
        return owner;
    }

    public void setOwner(SkribblPlayer owner) {
        this.owner = owner;
    }

    public Number getColor() {
        return color;
    }

    public void setColor(Number color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

