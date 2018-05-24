package ticketManager.communication;

import model.WebSocketSessionModel;

import javax.websocket.Session;

public interface IMessageProcessor {
    void onMessage(String string, WebSocketSessionModel session);
}
