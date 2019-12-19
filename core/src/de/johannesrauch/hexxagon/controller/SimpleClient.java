package de.johannesrauch.hexxagon.controller;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Diese Klasse erbt von org.java_websocket.client.WebSocketClient und wird benötigt um
 * eine WebSocket Verbindung zum Server aufzubauen.
 * 
 * Link zur Library: https://github.com/TooTallNate/Java-WebSocket
 * Link zum Wiki: https://github.com/TooTallNate/Java-WebSocket/wiki
 *
 * @author Dennis Jehle
 */
public class SimpleClient extends WebSocketClient {
	
	final Logger logger = LoggerFactory.getLogger(SimpleClient.class);
	
	final static Gson gson = new Gson();
	
	private ConnectionHandler connectionHandler;
	private MessageReceiver messageReceiver;
	
	public SimpleClient(URI serverUri
			, ConnectionHandler connectionHandler
			, MessageReceiver messageReceiver) {
		super(serverUri);
		this.connectionHandler = connectionHandler;
		this.messageReceiver = messageReceiver;
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		logger.info("SimpleClient connected to: " + this.getConnection().getRemoteSocketAddress().toString());
		connectionHandler.setConnection(this);
	}
	
	@Override
	public void onMessage(String json) {
		messageReceiver.handleMessage(json);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.info("SimpleClient disconnected from: " + this.getConnection().getRemoteSocketAddress().toString());
	}

	@Override
	public void onError(Exception ex) {
		logger.error("SimpleClient, onError executed: " + ex.getMessage());
	}

}
