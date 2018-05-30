package ticketManager.communication;

import ticketManager.model.WebSocketSessionModel;

public interface IMessageProcessor {
    void onMessage(String string, WebSocketSessionModel session);
}
