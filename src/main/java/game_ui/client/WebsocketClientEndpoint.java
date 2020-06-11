package game_ui.client;

import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.*;
import java.net.URI;
import java.util.HashSet;


@ClientEndpoint
public class WebsocketClientEndpoint {
    Session userSession = null;
    private MessageHandler messageHandler;

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
        System.out.println(message);
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }


    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);

    }

    public static interface MessageHandler {

        public void handleMessage(String message);
    }
}
