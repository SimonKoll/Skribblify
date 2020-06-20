package game_ui.client;

import game_ui.server.util.ConsoleColor;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.*;
import java.net.URI;


public class WebsocketClientEndpoint {
    public Session userSession = null;







    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);

    }



}
