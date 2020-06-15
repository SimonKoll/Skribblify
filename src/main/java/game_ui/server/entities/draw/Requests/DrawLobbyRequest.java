package game_ui.server.entities.draw.Requests;

import game_ui.server.entities.draw.DrawUser;

import java.util.ArrayList;
import java.util.List;

public class DrawLobbyRequest extends DrawRequest {
    private String lobbyID;
    private List<DrawUser> lobbyMembers = new ArrayList<>();
    private int maxPlayers;

    public DrawLobbyRequest(String type) {
        super(type);
    }


    public  DrawLobbyRequest(String type, String lobbyID) {
        super(type);
        this.lobbyID = lobbyID;
    }

    public DrawLobbyRequest(String type, String lobbyID, int maxPlayers) {
        super(type);
        this.lobbyID = lobbyID;
        this.maxPlayers = maxPlayers;
    }


    public String getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public List<DrawUser> getLobbyMembers() {
        return lobbyMembers;
    }

    public void setLobbyMembers(List<DrawUser> lobbyMembers) {
        this.lobbyMembers = lobbyMembers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
