package game_ui.server.endpoint;

import game_ui.server.entities.SkribblUser;
import game_ui.server.repository.SkribblRepo;
import game_ui.server.util.ConsoleColor;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/skribbl")
public class GameEndpoint {
    private SkribblRepo repo = SkribblRepo.getInstance();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.repo.addUser(new SkribblUser(session));
        System.out.println(ConsoleColor.yellow() + session.getId() + ConsoleColor.green() + " connected" + ConsoleColor.reset());
    }

    @OnMessage
    public void handleRequests(String request, Session session) {
        try{
          this.repo.handleRequest(new JSONObject(request), session);
        } catch(JSONException e){
            System.out.println(ConsoleColor.SERVER + "Invalid JSON Format!");
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t){
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session){
        this.repo.removeUser(session);
        System.out.println(ConsoleColor.SERVER + session.getId() + " Connection closed...");
    }
}
