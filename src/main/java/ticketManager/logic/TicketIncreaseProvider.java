package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;

public class TicketIncreaseProvider implements ITicketIncrease {
    private final MutationManager mutationManager;
    private final TicketRepository ticketRepository;

    public TicketIncreaseProvider(MutationManager mutationManager, TicketRepository context){
        this.mutationManager = mutationManager;
        this.ticketRepository = context;
    }

    public TicketMutationModel getAndIncrease(String id) {
        TicketModel model = ticketRepository.increaseAmountAndGetById(id);
        if(model == null){
            return null;
        }
        return mutationManager.createUpdateTicketMutation(model);
    }
}
