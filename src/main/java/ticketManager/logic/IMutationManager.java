package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;

public interface IMutationManager {
    TicketModel[] getMutationsAsTickets(int afterId);

    TicketMutationModel createNewTicketMutation(TicketModel ticket);

    TicketMutationModel createUpdateTicketMutation(TicketModel ticket);

    int getUpdateId();
}
