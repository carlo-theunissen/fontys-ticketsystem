package ticketManager.logic;

import ticketManager.model.WebSocketSessionModel;

public interface IWebSocketSessionStorage {
    WebSocketSessionModel[] getUnits();
}
