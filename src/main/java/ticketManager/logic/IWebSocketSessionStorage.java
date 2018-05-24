package ticketManager.logic;

import model.WebSocketSessionModel;

public interface IWebSocketSessionStorage {
    WebSocketSessionModel[] getUnits();
}
