package ticketManager.communication;

import model.TicketMutationModel;
import ticketManager.model.WebSocketSessionModel;
import ticketManager.logic.CheckUnitBroadcaster;
import ticketManager.logic.ITicketIncrease;


public class FromClientCommunication implements IMessageProcessor {
    private final ITicketIncrease increase;
    private final CheckUnitBroadcaster broadcaster;
    public FromClientCommunication(ITicketIncrease increase, CheckUnitBroadcaster broadcaster) {
        this.increase = increase;
        this.broadcaster = broadcaster;
    }

    public void onMessage(String ticketNumber, WebSocketSessionModel session) {
        handleTicketUpdate(ticketNumber);
    }
    private void handleTicketUpdate(String ticketNumber){
        TicketMutationModel mutation = this.increase.getAndIncrease(ticketNumber);
        this.broadcaster.send(mutation);
    }
}
