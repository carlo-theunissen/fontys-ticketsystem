package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;
import ticketManager.exceptions.TicketDuplicateException;

public class TicketCreator implements ITicketGenerator {
    private final IMutationManager IMutationManager;
    private final TicketRepository repository;

    public TicketCreator(IMutationManager IMutationManager, TicketRepository repository){
        this.IMutationManager = IMutationManager;
        this.repository = repository;
    }

    /**
     * Generates a new ticket, add it to the database and makes sure the mutation manager knows about it
     * @return the ticket number
     */
    public TicketMutationModel createTicket() throws TicketDuplicateException {
        TicketModel ticket = TicketModel.CreateNowTicketModel();
        ticket.setRandomId((int) (Math.random() * 1000 + 1));
        return postTicket(ticket);
    }

    public TicketMutationModel postTicket(TicketModel ticket) throws TicketDuplicateException {
        ticket = repository.newTicket(ticket);
        return IMutationManager.createNewTicketMutation(ticket);
    }

}
