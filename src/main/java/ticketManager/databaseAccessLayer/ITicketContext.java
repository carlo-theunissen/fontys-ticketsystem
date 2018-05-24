package ticketManager.databaseAccessLayer;

import model.TicketModel;

public interface ITicketContext {
    TicketModel increaseAmountAndGetById(int id);
    TicketModel[] getAllAfterId(int id);
    boolean postTicket(TicketModel ticket);
    TicketModel[] getAllValidAfterId(int id);
}
