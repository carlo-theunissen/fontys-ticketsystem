package ticketManager.communication;

import model.TicketMutationModel;
import model.WebSocketSessionModel;
import ticketManager.logic.CheckUnitBroadcaster;
import ticketManager.logic.ITicketIncrease;


public class FromClientCommunication implements IMessageProcessor {
    private final ITicketIncrease increase;
    private final CheckUnitBroadcaster broadcaster;
    public FromClientCommunication(ITicketIncrease increase, CheckUnitBroadcaster broadcaster) {
        this.increase = increase;
        this.broadcaster = broadcaster;
    }

    public void onMessage(String string, WebSocketSessionModel session) {
        try {
            int ticketNumber = Integer.parseInt(string);
            handleNewTicket(ticketNumber);
        } catch (Exception ignored){}

    }
    private void handleNewTicket(int ticketNumber){
        TicketMutationModel mutation = this.increase.getAndIncrease(ticketNumber);
        this.broadcaster.sendUpdate(mutation);
    }
}
