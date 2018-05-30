package ticketManager.logic;

import model.TicketModel;
import model.TicketMutationModel;

public class TicketIncreaseProvider implements ITicketIncrease {
    private final MutationManager mutationManager;
    private final TicketRepository ticketRepository;

    public TicketIncreaseProvider(MutationManager mutationManager, TicketRepository context){
        this.mutationManager = mutationManager;
        this.ticketRepository = context;
    }

    public TicketMutationModel getAndIncrease(String id) {
        TicketModel model = ticketRepository.increaseAmountAndGetById(id);
        return mutationManager.createUpdateTicketMutation(model);
    }
}
