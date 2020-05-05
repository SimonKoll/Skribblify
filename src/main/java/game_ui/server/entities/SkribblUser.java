package game_ui.server.entities;

import javax.websocket.Session;

public class SkribblUser {
    String username;
    private Session session;
    private Game game;
    private String color;

    public SkribblUser() {

    }

    public SkribblUser(final String username, final Session session, final Game game, final String color){
        this.username = username;
        this.session = session;
        this.game = game;
        this.color = color;
    }

    public SkribblUser(final Session session){
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Session getSession(){
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public boolean hasGame(){
        return this.game != null;
    }
}
