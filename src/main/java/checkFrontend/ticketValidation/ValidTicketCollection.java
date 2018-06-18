package checkFrontend.ticketValidation;

import checkFrontend.TicketResultModel;
import checkFrontend.TicketStatus;
import checkFrontend.interfaces.ITicketValidator;
import global.model.TicketModel;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

public class ValidTicketCollection extends Observable implements ITicketValidator {
    /**
     * guarded by this.validTickets itself
     */
    private final Map<String, Integer> validTickets = new ConcurrentHashMap<String, Integer>();

    public void reset() {
        validTickets.clear();
    }

    public TicketResultModel validate(String number) {
        Integer count = validTickets.get(number);
        TicketResultModel model = new TicketResultModel();

        if(count != null){
            model.setCount(count);
            model.setStatus(TicketStatus.VALID);
            return model;
        }
        model.setStatus(TicketStatus.INVALID);
        return model;
    }

    public boolean updateTicket(TicketModel ticket) {
        synchronized (validTickets) {
            Integer temp = validTickets.get(ticket.getTicketNumber());
            if(temp == null) {
                validTickets.put(ticket.getTicketNumber(), ticket.getAmountChecked());
            } else {
                validTickets.put(ticket.getTicketNumber(), Math.max(ticket.getAmountChecked(), temp));
            }
        }
        //do these lines after the synchronized block to prevent a deadlock
        setChanged();
        notifyObservers();
        return true;
    }

    public int getTotalTickets(){
        return validTickets.size();
    }
}
