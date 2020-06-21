package game_ui.server.encoder;

import javax.websocket.*;

import game_ui.server.entities.draw.Requests.DrawRequest;
import org.json.JSONObject;

public class DrawEncoder implements Encoder.Text<DrawRequest>{

    @Override
    public String encode(DrawRequest object) throws EncodeException {
        String jso = new JSONObject(object).toString();
        return jso;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
