package ticketManager.logic;

import com.google.gson.Gson;
import model.TicketMutationModel;
import model.WebSocketSessionModel;
import ticketManager.json.TicketUpdateMessage;

import java.io.IOException;

public class CheckUnitBroadcaster  {
    private final IWebSocketSessionStorage sessionStorage;

    public CheckUnitBroadcaster(IWebSocketSessionStorage unitStorage) {
        this.sessionStorage = unitStorage;
    }

    public void sendUpdate(TicketMutationModel ticket) {
        Gson gson = new Gson();
        TicketUpdateMessage message  = new TicketUpdateMessage();
        message.setCount(ticket.getTicket().getAmountChecked());
        message.setTicketNumber(ticket.getTicket().getTicketNumber());
        String text = gson.toJson(message);

        for (WebSocketSessionModel model : sessionStorage.getUnits()){
            try {
                model.getSession().getBasicRemote().sendText(text);
            } catch (IOException ignored) {

            }
        }
    }

    public void sendNew(TicketMutationModel ticket) {

    }
}
