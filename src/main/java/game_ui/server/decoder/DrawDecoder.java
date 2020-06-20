package game_ui.server.decoder;

import game_ui.server.entities.draw.Requests.*;
import org.json.JSONObject;

import javax.websocket.*;

public class DrawDecoder implements Decoder.Text<DrawRequest> {

    @Override
    public DrawRequest decode(String s) throws DecodeException {
        JSONObject jso = new JSONObject(s);
        DrawRequest request;

        switch (jso.getString("type")){
            case "login":
                request = new DrawLoginRequest(jso.getString("type"), jso.getString("username"));
                break;
            case "getGames":
                request = new DrawGameViewRequest(jso.getString("type"));
                break;
            case "createGame":
                request = new DrawLobbyRequest(jso.getString("type"), jso.getString("lobbyID"), jso.getInt("maxPlayers"), jso.getBoolean("visibility"), jso.getInt("roundSize"), jso.getInt("roundDuration"));
                break;
            case "joinLobby":
                request = new DrawLobbyRequest(jso.getString("type"), jso.getString("lobbyID"));
                break;
            case "leaveGame":
                request = new DrawLobbyRequest(jso.getString("type"), jso.getString("lobbyID"));
                break;
            case "startGame":
                request = new DrawGameRequest(jso.getString("type"));
                break;
            case "chat":
                request = new DrawChatRequest(jso.getString("type"), jso.getString("sender"), jso.getString("message"));
                break;
            case "updateGame":
                request = new DrawGameRequest(jso.getString("type"), jso.getString("game"),
                        jso.getDouble("x"), jso.getDouble("y"), jso.getBoolean("bucket"), jso.getBoolean("eraser"),  jso.getString("color"), jso.getDouble("size"));
                break;
            case "nextRound":
                request = new DrawGameRequest(jso.getString("type"));
                break;
            default:
                System.out.println("not Decode");
                throw new DecodeException(s, "Could not decode!");
        }
        return request;
    }

    @Override
    public boolean willDecode(String s) {
        JSONObject jso = new JSONObject(s);
        return jso.has("type");
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
