package ContextMock;

import global.model.TicketModel;
import ticketManager.databaseAccessLayer.ITicketContext;

import java.sql.SQLException;

public class UnitTestTicketContext implements ITicketContext {

    public String lastIncreaseId;
    public int lastAllAfterId;
    public int lastAllValidAfterId;
    public TicketModel lastCreateTicket;
    public TicketModel increaseAmountAndGetById(String id) {
        lastIncreaseId = id;
        return null;
    }

    public TicketModel[] getAllAfterId(int id) {
        lastAllAfterId = id;
        return new TicketModel[0];
    }

    public TicketModel[] getAllValidAfterId(int id) {
        lastAllValidAfterId = id;
        return new TicketModel[0];
    }

    public TicketModel createTicket(TicketModel model) throws SQLException {
        lastCreateTicket = model;
        return null;
    }
}
