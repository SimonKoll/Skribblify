package game_ui.server.repository;

import game_ui.server.entities.StatusMessage;
import game_ui.server.entities.draw.DrawGame;
import game_ui.server.entities.draw.DrawPlayer;
import game_ui.server.entities.draw.DrawUser;
import game_ui.server.entities.draw.Requests.*;
import game_ui.server.util.ConsoleColor;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawRepo {
    private static DrawRepo instance = null;
    private List<DrawUser> users = Collections.synchronizedList(new ArrayList<>());
    private List<DrawGame> games = new ArrayList<>();
    private List<DrawGame> availableGames = new ArrayList<>();
    private String[] topics = {"Haus", "Sommer", "Hund"};

    public static DrawRepo getInstance() {
        if (instance == null) {
            instance = new DrawRepo();
        }

        return instance;
    }

    public void handleRequest(DrawRequest request, Session session) {
        // Set the username

        if (request instanceof DrawLoginRequest) {
            this.updateUser((DrawLoginRequest) request, session);
        } else if (request instanceof DrawLobbyRequest) {
            // LOBBY
            this.handleLobbyRequest((DrawLobbyRequest) request, session);
        } else if (request instanceof DrawGameRequest) {
            // GAME (For the current drawer or the updating Views)
            this.handleGameRequests((DrawGameRequest) request, session);
        } else if (request instanceof DrawChatRequest) {
            // Chat for the guesses of the current game
            this.handleChatRequests((DrawChatRequest) request, session);
        } else if (request instanceof DrawGameViewRequest) {
            // Broadcast all the available Games
            this.handleGameList((DrawGameViewRequest) request, session);
        }
    }

    public void addUser(Session session) {
        this.users.add(new DrawUser(session));
    }

    public void removeUser(Session session) {
        System.out.println(ConsoleColor.GAME + findUserBySession(session).getUsername() + ConsoleColor.red() + " left the Game " + ConsoleColor.yellow() + findUserBySession(session).getGameID() + ConsoleColor.reset());
        this.users.removeIf(u -> u.getSession() == session);
    }


    private void updateUser(DrawLoginRequest dlr, Session s) {
        for (DrawUser du : this.users) {
            if (du.getSession() == s) {
                du.setUsername(dlr.getUsername());
                System.out.println("Username updated:" + dlr.getUsername());
            }
        }

        s.getAsyncRemote().sendText(new JSONObject().put("receivedSession", s.getId()).toString());
    }

    private DrawUser findUserBySession(Session s) {
        DrawUser tmpUser = new DrawUser(s);
        for (DrawUser du : this.users) {
            if (du.getSession() == s) {
                tmpUser = du;
                break;
            }
        }

        return tmpUser;
    }

    private DrawUser findUserBySessionID(String s){
        DrawUser tmpUser = new DrawUser();
        for (DrawUser du : this.users) {
            if(du.getSession().getId().equalsIgnoreCase(s)) {
                tmpUser = du;
                break;
            }
        }
        return tmpUser;
    }


    private DrawGame findGameById(String gameID) {
        DrawGame tmpGame = new DrawGame("");
        for (DrawGame g : this.games) {
            if(g.getGameID().equalsIgnoreCase(gameID)){
                tmpGame = g;
                break;
            }
        }

        return !tmpGame.getGameID().equalsIgnoreCase("") ? tmpGame : new DrawGame(gameID);
    }

    private DrawPlayer chooseRandomDrawer(String gameID){
        DrawGame game = findGameById(gameID);
        DrawPlayer drawer = game.getPlayers().get(randomIndex(0, game.getPlayers().size() - 1));
        game.setDrawer(drawer.getSessionID());
        this.games.removeIf(g -> g.getGameID().equalsIgnoreCase(gameID));
        this.games.add(game);
        return drawer;
    }

    private int randomIndex(final int from, final int to) {
        return (int) (Math.random() * (to-from)) + from;
    }

    private String chooseRandomTopic(String gameID) {
        DrawGame game = findGameById(gameID);
        // Später select aus DB
        String topic = this.topics[this.randomIndex(0, this.topics.length - 1)];
        game.setTopic(topic);
        this.games.removeIf(g -> g.getGameID().equalsIgnoreCase(gameID));
        this.games.add(game);
        return topic;
    }
    private void handleLobbyRequest(DrawLobbyRequest drl, Session session) {
        switch (drl.getType()) {
            case "createGame":
                this.createLobby(drl, session);
                break;
            case "joinLobby":
                boolean joined = false;
                DrawUser user = this.findUserBySession(session);
                for (DrawGame g : this.games) {
                    if (g.getGameID().equalsIgnoreCase(drl.getLobbyID())) {
                        user.setGameID(g.getGameID());
                        this.users.removeIf(u -> u.getSession() == user.getSession());
                        this.users.add(user);
                        g.getPlayers().add(new DrawPlayer(user.getSession().getId(), user.getUsername()));
                        this.games.remove(g);
                        this.games.add(g);

                        joined = true;

                        break;
                    }
                }

                if(joined) {
                    this.notifyToGame(user, "JOIN", null);
                    System.out.println(ConsoleColor.GAME + user.getUsername() + " joined the Game " + ConsoleColor.yellow() + drl.getLobbyID() + ConsoleColor.reset());
                } else {
                    System.out.println(ConsoleColor.GAME + user.getUsername() + " was not able to find and join the lobby: " + ConsoleColor.yellow() + drl.getLobbyID() + ConsoleColor.reset());

                }

                break;
            case "leaveGame":
                DrawUser us = this.findUserBySession(session);
                for (DrawGame g : this.games) {
                    if (g.getGameID().equalsIgnoreCase(drl.getLobbyID())) {
                        us.setGameID(g.getGameID());
                        g.getPlayers().removeIf(p -> p.getSessionID().equalsIgnoreCase(session.getId()));
                        break;
                    }
                }
                this.games.removeIf(ga -> ga.getPlayers().size() < 1);
                this.availableGames.clear();
                this.availableGames.addAll(this.games);
                refreshGameList(session);
                System.out.println(ConsoleColor.GAME + findUserBySession(session).getUsername() + ConsoleColor.red() + " left the Game " + ConsoleColor.yellow() + drl.getLobbyID() + ConsoleColor.reset());
                this.notifyToGame(us, "LEAVE", null);
                break;
            default:
                break;
        }

    }

    // -= Game-Handler =-
    private DrawGame gameToRemove = new DrawGame("");

    private void handleGameRequests(DrawGameRequest dgr, Session session) {

        if (dgr.getType().equalsIgnoreCase("startGame")) {
            for (DrawGame dg : this.games) {
                for (DrawPlayer dp : dg.getPlayers()) {
                    if (dp.getSessionID().equalsIgnoreCase(session.getId())) {
                        gameToRemove = dg;
                        break;
                    }
                }
            }
            this.availableGames.removeIf(g -> g.getGameID().equalsIgnoreCase(gameToRemove.getGameID()));
            notifyToGame(findUserBySession(session), "START", null);
            this.refreshGameList(session);
        } else if (dgr.getType().equalsIgnoreCase("updateGame")) {
            for (DrawGame g : this.games) {
                if (g.getGameID().equalsIgnoreCase(dgr.getGameID())) {
                    for (DrawPlayer p : g.getPlayers()) {
                        DrawUser du = findUserBySessionID(p.getSessionID());
                        if (du != findUserBySession(session)) {
                            du.getSession().getAsyncRemote().sendObject(dgr);
                        }
                    }
                }
            }
        } else if (dgr.getType().equalsIgnoreCase("nextRound")) {
            notifyToGame(findUserBySession(session), "START", null);
        }
    }

    // -= Chat-Handler =-
    private void handleChatRequests(DrawChatRequest hcr, Session session) {
        DrawUser tmpU = findUserBySession(session);
        for (DrawUser u : this.users) {
            if (u.getGameID().equalsIgnoreCase(tmpU.getGameID())) {
                DrawGame g = findGameById(tmpU.getGameID());
                if (!hcr.getSender().equalsIgnoreCase(g.getDrawer()) &&
                        hcr.getMessage().equalsIgnoreCase(g.getTopic())) { // this.findGameByID(tmpU.getGameID()).getTopic()
                    this.sendStatusMessage(new StatusMessage(155, hcr.getSender() + "| has guessed the Word!"), u.getSession()); // 155 Word guessed
                    if (!g.getGuessedRight().contains(hcr.getSender()) || g.getGuessedRight().size() < 1) {
                        g.getGuessedRight().add(hcr.getSender());
                    }
                } else {
                    u.getSession().getAsyncRemote().sendObject(hcr);
                }
            }
        }
    }

    // -= GameList-Handler =-
    private void handleGameList(DrawGameViewRequest dgvr, Session session) {
        this.refreshGameList(session);

    }

    // Sends the Status message
    private void sendStatusMessage(StatusMessage statusMessage, Session session) {
        session.getAsyncRemote().sendText(new JSONObject().put("type", "status").put("code", statusMessage.getStatusCode()).put("message", statusMessage.getStatusMessage()).toString());
    }

    // Creates the Lobby of a Game
    private void createLobby(DrawLobbyRequest drl, Session session) {
        boolean exists = false;
        for (DrawGame dg : this.games) {
            if (dg.getGameID().equalsIgnoreCase(drl.getLobbyID())) {
                this.sendStatusMessage(new StatusMessage(499, "Game creation failed"), session);
                exists = true;
            }
        }
        if (!exists) {
            DrawGame tmpGame = new DrawGame(session, drl.getLobbyID());

            this.sendStatusMessage(new StatusMessage(199, "Game successfully created"), session);
            System.out.println(ConsoleColor.SERVER + ConsoleColor.green() + "DRAW: Game " + drl.getLobbyID().toUpperCase() + " successfully created.");
            drl.getLobbyMembers().add(this.findUserBySession(session));
            DrawUser tmpU = this.findUserBySession(session);
            tmpGame.getPlayers().add(new DrawPlayer(tmpU.getSession().getId(), tmpU.getUsername()));
            tmpU.setGameID(tmpGame.getGameID());
            this.users.removeIf(u -> u.getSession() == tmpU.getSession());
            this.users.add(tmpU);
            this.games.add(tmpGame);
            this.availableGames.addAll(games);
            this.refreshGameList(session);
            this.notifyToGame(tmpU, "JOIN", null);
        } else {
            this.sendStatusMessage(new StatusMessage(499, "Game already exists. Try another GameID"), session);
        }

    }

    // Broadcasts all available Games to the connected Users
    private void refreshGameList(Session session) {
        JSONObject obj = new JSONObject();
        obj.put("type", "games");


        obj.put("games", this.availableGames.stream().filter(e -> e.getPlayers().size() != 0).toArray());
        // session.getAsyncRemote().sendText(obj.toString());

        // Broadcast to all Users without a Game
        for (DrawUser user : this.users) {
            if (!user.hasGame()) {
                user.getSession().getAsyncRemote().sendText(obj.toString());
            }
        }
    }

    // Notifies Events to a specific Game
    private void notifyToGame(DrawUser user, String notifyType, DrawRequest request) {
        switch (notifyType.toUpperCase()) {
            case "JOIN":
                for (DrawUser u : this.users) {
                    if (u.getGameID().equalsIgnoreCase(user.getGameID())) {
                        u.getSession().getAsyncRemote().sendText(new JSONObject().put("type", "join").put("game", new JSONObject(findGameById(u.getGameID()))).toString());
                    }
                }
                break;
            case "LEAVE":
               /* for (DrawUser u : this.users) {
                    if (u.getGameID().equalsIgnoreCase(user.getGameID())) {
                        this.users.removeIf(us -> us.getSession() == user.getSession());
                        u.getSession().getAsyncRemote().sendText(new JSONObject().put("type", "leave").put("game", new JSONObject(findGameByID(u.getGameID()))).toString());
                    }
                }*/
                // this.users.removeIf(u -> u.getSession() == user.getSession());
                break;
            case "START":
                DrawPlayer drawPlayer = this.chooseRandomDrawer(user.getGameID());
                System.out.println(ConsoleColor.GAME + ConsoleColor.purple() + user.getGameID() + ConsoleColor.reset() + " " + "The new Drawer is: " + ConsoleColor.green() + drawPlayer.getUsername() + ConsoleColor.reset());
                for (DrawUser u : this.users) {
                    if (u.getGameID().equalsIgnoreCase(user.getGameID())) {
                        u.getSession().getAsyncRemote().sendText(new JSONObject().put("type", "start").put("drawer", drawPlayer.getSessionID()).toString());
                    }
                }
                // SEND THE TOPIC TO THE DRAWER
                findUserBySessionID(drawPlayer.getSessionID()).getSession().getAsyncRemote().sendText(new JSONObject().put("type", "topic").put("topic", this.chooseRandomTopic(user.getGameID())).toString());
                break;
            default:
                break;
        }
    }

}
