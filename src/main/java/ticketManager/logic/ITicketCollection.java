package ticketManager.logic;

import model.TicketModel;

public interface ITicketCollection {
    TicketModel[] getValidTickets(int afterId);

    TicketModel[] getStartDump();
}
