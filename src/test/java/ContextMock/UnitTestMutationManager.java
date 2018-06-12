package ContextMock;

import global.model.TicketModel;
import global.model.TicketMutationModel;
import ticketManager.logic.IMutationManager;

public class UnitTestMutationManager implements IMutationManager {
    private int lastGetMutationsAsTickets;
    private TicketModel lastCreateNewTicketMutation;
    private TicketModel lastCreateUpdateTicketMutation;

    public TicketModel[] getMutationsAsTickets(int afterId) {
        lastGetMutationsAsTickets = afterId;
        return new TicketModel[0];
    }

    public TicketMutationModel createNewTicketMutation(TicketModel ticket) {
        lastCreateNewTicketMutation = ticket;
        return null;
    }

    public TicketMutationModel createUpdateTicketMutation(TicketModel ticket) {
        lastCreateUpdateTicketMutation = ticket;
        return null;
    }

    public int getUpdateId() {
        return 0;
    }

    public int getLastGetMutationsAsTickets() {
        return lastGetMutationsAsTickets;
    }

    public TicketModel getLastCreateNewTicketMutation() {
        return lastCreateNewTicketMutation;
    }

    public TicketModel getLastCreateUpdateTicketMutation() {
        return lastCreateUpdateTicketMutation;
    }
}
