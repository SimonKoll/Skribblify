package game_ui.server.endpoint;


import game_ui.server.decoder.DrawDecoder;
import game_ui.server.util.ConsoleColor;

import java.io.IOException;
import org.json.JSONException;

@ServerEndpoint(value = "/draw",encoders = DrawEncoder.class, decoders = DrawDecoder.class)
public class DrawEndpoint {
    DrawRepo drawRepo = DrawRepo.getInstance();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.drawRepo.addUser(session);
        System.out.println(ConsoleColor.yellow() + session.getId() + ConsoleColor.green() + " connected" + ConsoleColor.reset());
    }

    @OnMessage
    public void handleRequests(DrawRequest request, Session session) {
        try {
            this.drawRepo.handleRequest(request, session);
        } catch (JSONException e) {
            System.out.println(ConsoleColor.SERVER + "Invalid JSON Format!");
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        this.drawRepo.removeUser(session);
        System.out.println(ConsoleColor.SERVER + session.getId() + " Connection closed...");
    }

}
