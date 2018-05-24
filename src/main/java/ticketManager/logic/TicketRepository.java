package ticketManager.logic;

import model.TicketModel;
import ticketManager.databaseAccessLayer.ITicketContext;

public class TicketRepository {
    private final ITicketContext context;

    public TicketRepository(ITicketContext context) {
        this.context = context;
    }

    public TicketModel increaseAmountAndGetById(int id){
        return context.increaseAmountAndGetById(id);
    }
    public TicketModel[] getAllAfterId(int id) {
        return context.getAllAfterId(id);
    }
    public TicketModel[] getAllValidAfterId(int id){
        return context.getAllValidAfterId(id);
    }
}
