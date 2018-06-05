package ticketManager.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.model.TicketExternalCommunicationModel;
import global.model.TicketMutationModel;
import ticketManager.model.WebSocketSessionModel;
import ticketManager.logic.CheckUnitBroadcaster;
import ticketManager.logic.ITicketIncrease;

import java.text.DateFormat;


public class FromClientCommunication implements IMessageProcessor {
    private final ITicketIncrease increase;
    private final CheckUnitBroadcaster broadcaster;
    public FromClientCommunication(ITicketIncrease increase, CheckUnitBroadcaster broadcaster) {
        this.increase = increase;
        this.broadcaster = broadcaster;
    }

    public void onMessage(String message, WebSocketSessionModel session) {
        Gson gson = new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        TicketExternalCommunicationModel communicationModel = gson.fromJson(message, TicketExternalCommunicationModel.class);
        handleTicketUpdate(communicationModel.getTicketNumber());
    }
    private void handleTicketUpdate(String ticketNumber){
        TicketMutationModel mutation = this.increase.getAndIncrease(ticketNumber);
        if(mutation != null) {
            this.broadcaster.send(mutation);
        }
    }
}
