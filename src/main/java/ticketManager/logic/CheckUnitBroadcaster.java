package ticketManager.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.model.TicketMutationModel;
import global.model.TicketExternalCommunicationModel;
import ticketManager.model.WebSocketSessionModel;

import java.io.IOException;
import java.text.DateFormat;

public class CheckUnitBroadcaster  {
    private final IWebSocketSessionStorage sessionStorage;

    public CheckUnitBroadcaster(IWebSocketSessionStorage unitStorage) {
        this.sessionStorage = unitStorage;
    }

    public void send(TicketMutationModel ticket) {
        Gson gson = new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        String text = gson.toJson(new TicketExternalCommunicationModel(ticket.getTicket()));

        for (WebSocketSessionModel model : sessionStorage.getUnits()){
            try {
                model.getSession().getBasicRemote().sendText(text);
            } catch (IOException ignored) {

            }
        }
    }
}
