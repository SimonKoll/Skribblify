package game_ui.server.entities.draw;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawGame {
    private String hostID;
    private String gameID;
    private int maxPlayers;
    private List<DrawPlayer> players = new ArrayList<>();
    private String word;
    private String drawer;
    private transient Set<String> guessedRight = new HashSet<>();
    private String topic;

    public DrawGame(String gameID, int maxPlayers, Session host){
        this.gameID = gameID;
        this.maxPlayers = maxPlayers;
        this.hostID = host.getId();
    }

    public DrawGame(Session host, String gameID){
        this.hostID = host.getId();
        this.gameID = gameID;
    }

    public DrawGame(String gameID) {
        this.gameID = gameID;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<DrawPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<DrawPlayer> players) {
        this.players = players;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public Set<String> getGuessedRight() {
        return guessedRight;
    }

    public void setGuessedRight(Set<String> guessedRight) {
        this.guessedRight = guessedRight;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
