package ticketManager.logic;

import global.model.TicketModel;

public class TicketCollectionProvider implements ITicketCollection {
    private final TicketRepository repository;

    public TicketCollectionProvider(TicketRepository repository) {
        this.repository = repository;
    }

    public TicketModel[] getValidTickets(int afterId){
        return this.repository.getAllValidAfterId(afterId);
    }
    public TicketModel[] getStartDump(){
        return this.repository.getAllAfterId(0);
    }
}
