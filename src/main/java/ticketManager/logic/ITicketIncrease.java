package ticketManager.logic;

import model.TicketMutationModel;

public interface ITicketIncrease {
    TicketMutationModel getAndIncrease(int id);
}
