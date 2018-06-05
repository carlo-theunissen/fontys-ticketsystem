package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;
import ticketManager.exceptions.TicketDuplicateException;

public interface ITicketGenerator {
    TicketMutationModel createTicket() throws TicketDuplicateException;
    TicketMutationModel postTicket(TicketModel ticket) throws TicketDuplicateException;
}
