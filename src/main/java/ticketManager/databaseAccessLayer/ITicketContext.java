package ticketManager.databaseAccessLayer;

import model.TicketModel;

import java.sql.SQLException;

public interface ITicketContext {
    TicketModel increaseAmountAndGetById(String id);
    TicketModel[] getAllAfterId(int id);
    TicketModel[] getAllValidAfterId(int id);
    TicketModel createTicket(TicketModel model) throws SQLException;
}
