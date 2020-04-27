package game_ui.server.encoder;

import javax.websocket.*;
import org.json.JSONObject;

public class DrawEncoder implements Encoder.Text<DrawRequest>{

    @Override
    public String encode(DrawRequest object) throws EncodeException {
        String jso = new JSONObject(object).toString();
        System.out.println("Encoded: " + jso);
        return jso;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
