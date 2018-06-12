package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;

public class TicketIncreaseProvider implements ITicketIncrease {
    private final IMutationManager IMutationManager;
    private final TicketRepository ticketRepository;

    public TicketIncreaseProvider(IMutationManager IMutationManager, TicketRepository context){
        this.IMutationManager = IMutationManager;
        this.ticketRepository = context;
    }

    public TicketMutationModel getAndIncrease(String id) {
        TicketModel model = ticketRepository.increaseAmountAndGetById(id);
        if(model == null){
            return null;
        }
        return IMutationManager.createUpdateTicketMutation(model);
    }
}
