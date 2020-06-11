package game_ui.client.test;

import game_ui.client.WebsocketClientEndpoint;

import java.net.URI;
import java.net.URISyntaxException;


public class TestApp {
    public static void main(String[] args) {

        try {

            final WebsocketClientEndpoint clientGameEndpoint = new WebsocketClientEndpoint(new URI("ws://localhost:8025/websockets/skribbl"));
            clientGameEndpoint.addMessageHandler(message -> System.out.println(message));
            clientGameEndpoint.sendMessage("{\"type\": \"login\", \"username\": \"Thomas\"}");


            Thread.sleep(10000);
        } catch (URISyntaxException | InterruptedException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
