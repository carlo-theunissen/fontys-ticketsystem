package ticketManager.databaseAccessLayer;

import model.TicketModel;

public class DatabaseTicketContext implements ITicketContext {
    public TicketModel increaseAmountAndGetById(int id) {
        return null;
    }

    public TicketModel[] getAllAfterId(int id) {
        return new TicketModel[0];
    }

    public boolean postTicket(TicketModel ticket) {
        return false;
    }

    public TicketModel[] getAllValidAfterId(int id) {
        return new TicketModel[0];
    }
}
