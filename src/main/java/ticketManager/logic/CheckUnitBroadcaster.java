package ticketManager.logic;

import com.google.gson.Gson;
import model.TicketMutationModel;
import ticketManager.model.TicketExternalCommunicationModel;
import ticketManager.model.WebSocketSessionModel;

import java.io.IOException;

public class CheckUnitBroadcaster  {
    private final IWebSocketSessionStorage sessionStorage;

    public CheckUnitBroadcaster(IWebSocketSessionStorage unitStorage) {
        this.sessionStorage = unitStorage;
    }

    public void send(TicketMutationModel ticket) {
        Gson gson = new Gson();
        String text = gson.toJson(new TicketExternalCommunicationModel(ticket.getTicket()));

        for (WebSocketSessionModel model : sessionStorage.getUnits()){
            try {
                model.getSession().getBasicRemote().sendText(text);
            } catch (IOException ignored) {

            }
        }
    }
}
