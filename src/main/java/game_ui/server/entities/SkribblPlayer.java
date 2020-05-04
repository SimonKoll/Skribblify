package game_ui.server.entities;

public class SkribblPlayer extends SkribblUser{

    private String game;
    private boolean isAdmin;
    private String color;

    public SkribblPlayer(SkribblUser user){
        super.username = user.getUsername();
    }

    public SkribblPlayer(){

    }
    public String getGameID() {
        return game;
    }

    public void setGameID(String game) {
        this.game = game;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

}
