package ticketManager.logic;

import model.TicketModel;
import model.TicketMutationModel;
import ticketManager.databaseAccessLayer.ITicketContext;

public class TicketCreator implements ITicketGenerator {
    private final MutationManager mutationManager;
    private final ITicketContext ticketContext;

    public TicketCreator(MutationManager mutationManager, ITicketContext context){
        this.mutationManager = mutationManager;
        this.ticketContext = context;
    }

    /**
     * Generates a new ticket, add it to the database and makes sure the mutation manager knows about it
     * @return the ticket number
     */
    public TicketMutationModel newTicket() {
        TicketModel ticket = TicketModel.CreateNowTicketModel();
        ticketContext.postTicket(ticket);
        return mutationManager.createNewTicketMutation(ticket);
    }
}
