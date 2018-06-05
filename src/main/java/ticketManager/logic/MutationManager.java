package ticketManager.logic;

import global.model.TicketModel;
import global.model.TicketMutationModel;
import global.model.TicketMutationType;

import java.util.ArrayList;

public class MutationManager {
    private ArrayList<TicketMutationModel> mutations;

    public MutationManager(){
        mutations = new ArrayList<TicketMutationModel>();
    }

    public TicketModel[] getMutationsAsTickets(int afterId){
        if(mutations.size() < afterId){
            return new TicketModel[0];
        }
        ArrayList<TicketModel> outcome = new ArrayList<TicketModel>();
        TicketMutationModel[] array = (TicketMutationModel[]) mutations.toArray();
        for(int i = afterId;  i < array.length; i++){
            if(!outcome.contains(array[i].getTicket())){
                outcome.add(array[i].getTicket());
            }
        }

        return (TicketModel[]) outcome.toArray();
    }
    public TicketMutationModel createNewTicketMutation(TicketModel ticket){
        TicketMutationModel mutationModel = new TicketMutationModel(ticket, TicketMutationType.NEW, mutations.size());
        mutations.add(mutationModel);
        return mutationModel;
    }
    public TicketMutationModel createUpdateTicketMutation(TicketModel ticket){
        TicketMutationModel mutationModel = new TicketMutationModel(ticket, TicketMutationType.UPDATE, mutations.size());
        mutations.add(mutationModel);
        return mutationModel;
    }
    public int getUpdateId(){
        return mutations.size();
    }
}
