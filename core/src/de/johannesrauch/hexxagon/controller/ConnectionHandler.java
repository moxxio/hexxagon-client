package de.johannesrauch.hexxagon.controller;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.clients.SimpleClient;
import org.java_websocket.WebSocket;

/**
 * Diese Klasse verwaltet eine WebSocket Verbindung zu einem Spielserver.
 *
 * @author Dennis Jehle
 */
public class ConnectionHandler {

    private SimpleClient simpleClient;
    private WebSocket connection;
    private UUID userId;

    public void setSimpleClient(SimpleClient simpleClient) {
        this.simpleClient = simpleClient;
    }

    public SimpleClient getSimpleClient() {
        if (simpleClient == null) {
            reset();
        }

        return simpleClient;
    }

    public void setConnection(WebSocket connection) {
        if (this.connection == null) {
            this.userId = null;
            this.connection = connection;
            this.connection.setAttachment(null);
        } else {
            closeConnection();
            setConnection(connection);
        }
    }

    public WebSocket getConnection() {
        if (connection == null) {
            return null;
        }

        if (userId.equals((UUID) connection.getAttachment())) {
            return connection;
        }

        return null;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
        this.connection.setAttachment(this.userId);
    }

    public UUID getUserId() {
        return userId;
    }

    public ConnectionHandler() {
        reset();
    }

    public boolean connectionOpen() {
        if (connection == null) {
            return false;
        }

        if (connection.isOpen()) {
            return true;
        } else {
            closeConnection();
            return false;
        }
    }

    public void closeConnection() {
        if (connection != null) {
            connection.close();
            reset();
        }
    }

    private void reset() {
        this.simpleClient = null;
        this.connection = null;
        this.userId = null;
    }
}
