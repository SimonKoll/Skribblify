package game_ui.server.repository;

import game_ui.server.entities.Game;
import game_ui.server.entities.SkribblUser;
import game_ui.server.entities.StatusMessage;
import game_ui.server.util.ConsoleColor;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SkribblRepo {
    private static SkribblRepo instance = null;
    private List<SkribblUser> users = Collections.synchronizedList(new ArrayList<SkribblUser>());
    private List<Game> games = Collections.synchronizedList(new CopyOnWriteArrayList<Game>());
    private String[] colors = {"0xfeb74c", "0x1abc9c", "0x3b78a4"};

    public static SkribblRepo getInstance(){
        if(instance == null){
            instance = new SkribblRepo();
        }
        return instance;
    }

    public void handleRequest(JSONObject request, Session session) {
        if (this.hasRequiredParams(request)){
            switch (request.getString("type")){
                case "createGame":
                    boolean check = false;
                    for (Game g : this.games){
                        if(g.getGameID().equals(request.getString("game"))) {
                            check = true;
                            break;
                        }
                    }

                    if(check) {
                        this.sendStatusMessage(new StatusMessage(499, "Game already exists. Try another GameID"), session);
                    }else {
                        this.games.add(new Game(request.getString("game")));
                        this.sendStatusMessage(new StatusMessage(199, "Game Successfully created."), session);
                        System.out.println(ConsoleColor.SERVER + ConsoleColor.green() + "Game " + request.getString("game").toUpperCase() + " successfully created.");
                        this.joinGame(session, new Game(request.getString("game")));
                        this.refreshGameList(session);
                    }

                    break;

                case "join":
                    this.joinGame(session, new Game(request.getString("game")));
                    break;
                case "update":
                    this.notifyToGame(null, "UPDATE", request);
                    break;
                default:
                    break;
            }
        }else if(this.hasRequiredAuthParams(request)){
            switch (request.getString("type")) {
                case "login":
                    this.updateUser(session, request.getString("username"));
                    break;
                default:
                    break;
            }
        } else {
            if(request.has("type") && request.getString("type").equalsIgnoreCase("getGames")) {
                this.refreshGameList(session);
            }
        }
    }

    public void addUser(SkribblUser user) {
        user.setColor(this.colors[(int) Math.floor(Math.random() * colors.length)]);
        System.out.println(user.getColor());
        this.users.add(user);
    }

    public void removeUser(Session session) {
        this.users.removeIf(u -> u.getSession() == session);
    }

    private void updateUser(Session session, String username) {
        for (SkribblUser su : this.users) {
            if (su.getSession() == session) {
                su.setUsername(username);
                System.out.println(ConsoleColor.SERVER + "Username successfully set! ");
            }
        }
    }

    private boolean hasRequiredParams(JSONObject object) {
        return object.has("type") && object.has("game");
    }

    private boolean hasRequiredAuthParams(JSONObject object) {
        return object.has("type") && object.has("username");
    }

    private void joinGame(Session session, Game game) {
        for (SkribblUser su : this.users) {
            if (su.getSession() == session) {
                su.setGame(game);
                this.notifyToGame(su, "JOIN", null);
                System.out.println(ConsoleColor.GAME + su.getUsername() + " joined the Game " + ConsoleColor.yellow() + game.getGameID() + ConsoleColor.reset());
            }
        }
    }

    private void notifyToGame(SkribblUser user, String notifyType, JSONObject request) {
        JSONObject obj = new JSONObject();
        switch (notifyType){
            case "JOIN":
                for (SkribblUser su : this.users) {
                    if (su.getGame() == user.getGame()) {
                        obj.put("type", "info");
                        obj.put("message", user.getUsername() + " joined the Game.");
                        su.getSession().getAsyncRemote().sendText(obj.toString());
                        break;
                    }
                }
                break;

            case "LEAVE":
                for (SkribblUser su : this.users) {
                    if(su.getGame() == user.getGame()) {
                        obj.put("type", "info");
                        obj.put("message", su.getUsername() + " left the Game.");
                        su.getSession().getAsyncRemote().sendText(obj.toString());
                        break;
                    }
                }
                break;

            case "UPDATE":
                if(request.get("players") != null && request.get("cubes") != null && request.get("game") != null) {
                    for (SkribblUser su : this.users) {
                        if (su != null) {
                            System.out.println(request.getString("game"));
                            if(su.getGame().getGameID().equalsIgnoreCase(request.getString("game"))) {
                                su.getSession().getAsyncRemote().sendText(request.toString());
                            }
                        }
                    }
                }
                break;

            default:
                break;

            }
        }


        private void refreshGameList(Session session) {
            JSONObject obj = new JSONObject();
            obj.put("type", "games");
            obj.put("games", this.games.toArray());

            for (SkribblUser user : this.users) {
                if (!user.hasGame()) {
                    user.getSession().getAsyncRemote().sendText(obj.toString());
                }
            }
        }

        private void sendStatusMessage(StatusMessage statusMessage, Session session) {
            session.getAsyncRemote().sendText(new JSONObject().put("type", "status").put("code", statusMessage.getStatusCode()).put("message", statusMessage.getStatusMessage()).toString());
        }
    }


