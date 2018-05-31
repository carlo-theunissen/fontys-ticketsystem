package ticketManager.logic;

import model.TicketModel;
import ticketManager.databaseAccessLayer.ITicketContext;
import ticketManager.exceptions.TicketDuplicateException;

import java.sql.SQLException;

public class TicketRepository {
    private final ITicketContext context;

    public TicketRepository(ITicketContext context) {
        this.context = context;
    }

    public TicketModel increaseAmountAndGetById(String id){
        return context.increaseAmountAndGetById(id);
    }
    public TicketModel[] getAllAfterId(int id) {
        return context.getAllAfterId(id);
    }
    public TicketModel[] getAllValidAfterId(int id){
        return context.getAllValidAfterId(id);
    }

    public TicketModel newTicket(TicketModel ticket) throws TicketDuplicateException {
        try {
            return context.createTicket(ticket);
        } catch (SQLException e) {
            throw new TicketDuplicateException();
        }
    }
}
