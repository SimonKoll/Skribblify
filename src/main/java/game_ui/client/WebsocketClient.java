package game_ui.client;

import javafx.stage.Stage;
import login_registration.model.User;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.net.URI;
import java.sql.Statement;

public abstract class WebsocketClient {

    public Session userSession = null;

    @OnMessage
    public abstract void onMessage(String Message);

    public abstract void show(Stage stage, Statement statement, User user);



    @OnOpen
    public abstract void onOpen(Session session);

    @OnClose
    public abstract void onClose(Session userSession, CloseReason reason);


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void initializeClient(URI endpointURI) {
        try {
            ClientManager client = ClientManager.createClient();
            client.connectToServer(this, endpointURI);
            System.out.println("connection established...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
