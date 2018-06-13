package checkFrontend.ticketValidation;

import checkFrontend.TicketResultModel;
import checkFrontend.TicketStatus;
import checkFrontend.interfaces.ITicketCommunicator;
import global.model.TicketModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OfflineTicketValidation  {
    private final ITicketCommunicator websocketCommunicator;

    public OfflineTicketValidation(ITicketCommunicator websocketCommunicator) {
        this.websocketCommunicator = websocketCommunicator;
    }

    public TicketResultModel validate(TicketModel ticket) {

        if(ticket == null || ticket.getDate().before(websocketCommunicator.getLastUpdateDate())){
            TicketResultModel model = new TicketResultModel();
            model.setStatus(TicketStatus.INVALID);
            return model;
        }

        long diff = Math.abs( new Date().getTime() - ticket.getDate().getTime() );
        if(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) > 15){
            TicketResultModel model = new TicketResultModel();
            model.setStatus(TicketStatus.INVALID);
            return model;
        }
        TicketResultModel model = new TicketResultModel();
        model.setStatus(TicketStatus.UNKNOWN);
        return model;
    }

    public boolean updateTicket(TicketModel ticket) {
        return false;
    }
}
