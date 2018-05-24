package ticketManager.logic;

import model.TicketModel;

public class TicketCollectionProvider {
    private final TicketRepository repository;

    public TicketCollectionProvider(TicketRepository repository) {
        this.repository = repository;
    }

    public TicketModel[] getValidTickets(int afterId){
        return this.repository.getAllValidAfterId(0);
    }
    public TicketModel[] getStartDump(){
        return getValidTickets(0);
    }
}
