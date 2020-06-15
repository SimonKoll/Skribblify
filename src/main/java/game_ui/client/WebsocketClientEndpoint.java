package game_ui.client;

import game_ui.server.util.ConsoleColor;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.*;
import java.io.Console;
import java.net.URI;
import java.util.HashSet;


@ClientEndpoint
public class WebsocketClientEndpoint {
    public Session userSession = null;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            ClientManager client = ClientManager.createClient();
            client.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        JSONObject jso = new JSONObject(message);

        System.out.println(jso);


        switch(jso.getString("type")){
            case "join":
                System.out.println(ConsoleColor.PURPLE + "New Player joined:" + jso.getJSONObject("game").getJSONArray("players"));
                break;
        }
    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);

    }



}
