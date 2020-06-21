package game_ui.server.entities.draw;

import javax.websocket.Session;

public class DrawUser {
    private String username;
    private Session session;
    private String gameID = "";
    private int points = 0;

    public DrawUser(){

    }
    public DrawUser(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public boolean hasGame(){
        return !this.gameID.equalsIgnoreCase("");
    }


    @Override
    public String toString() {
        return "DrawUser{" +
                "username='" + username + '\'' +
                ", session=" + session +
                ", gameID='" + gameID + '\'' +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
