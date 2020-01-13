package de.johannesrauch.hexxagon.test.tools;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.network.message.AbstractMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This websocket client instantiation is used to test the send messages.
 * You should override the send method in order to check the validity of the messages.
 * This trick is used to "catch" the messages before they will be sent over the socket.
 */
public class ClientDummy extends WebSocketClient {
    protected final Gson gson = new Gson();

    protected AbstractMessage message;

    protected boolean sent;

    public ClientDummy(AbstractMessage message) throws URISyntaxException {
        super(new URI("ws://localhost:4444"));
        this.message = message;
        sent = false;
    }

    public boolean hasSent() {
        return sent;
    }

    // Dummy methods
    @Override
    public void onOpen(ServerHandshake handshake) {
    }

    @Override
    public void onMessage(String message) {
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
    }
}
