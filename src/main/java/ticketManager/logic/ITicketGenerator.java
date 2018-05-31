package ticketManager.logic;

import model.TicketModel;
import model.TicketMutationModel;
import ticketManager.exceptions.TicketDuplicateException;

public interface ITicketGenerator {
    TicketMutationModel createTicket() throws TicketDuplicateException;
    TicketMutationModel postTicket(TicketModel ticket) throws TicketDuplicateException;
}
