package ticketManager.logic;

import global.model.TicketModel;

public interface ITicketCollection {
    TicketModel[] getValidTickets(int afterId);

    TicketModel[] getStartDump();
}
