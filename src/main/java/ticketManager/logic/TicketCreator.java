package ticketManager.logic;

import model.TicketModel;
import model.TicketMutationModel;
import ticketManager.databaseAccessLayer.ITicketContext;

public class TicketCreator implements ITicketGenerator {
    private final MutationManager mutationManager;
    private final TicketRepository repository;

    public TicketCreator(MutationManager mutationManager, TicketRepository repository){
        this.mutationManager = mutationManager;
        this.repository = repository;
    }

    /**
     * Generates a new ticket, add it to the database and makes sure the mutation manager knows about it
     * @return the ticket number
     */
    public TicketMutationModel newTicket() {
        TicketModel ticket = TicketModel.CreateNowTicketModel();
        ticket = repository.newTicket(ticket);
        return mutationManager.createNewTicketMutation(ticket);
    }
}
