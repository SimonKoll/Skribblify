package game_ui.server.entities.draw;

public class DrawPlayer {
    private String sessionID;
    private String username;
    private int points = 0;

    public DrawPlayer(String sessionID){

    }
    public DrawPlayer(String sessionID, String username){
        this.sessionID = sessionID;
        this.username = username;
        this.points = 0;
    }



    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
