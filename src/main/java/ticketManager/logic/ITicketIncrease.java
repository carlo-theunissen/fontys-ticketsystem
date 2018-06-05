package ticketManager.logic;

import global.model.TicketMutationModel;

public interface ITicketIncrease {
    TicketMutationModel getAndIncrease(String id);
}
