package checkFrontend.ticketValidation;

import checkFrontend.TicketResultModel;
import checkFrontend.TicketStatus;
import checkFrontend.communication.RestTicketCommunicator;
import checkFrontend.communication.WebSocketTicketCommunicator;
import checkFrontend.interfaces.ITicketCommunicator;
import checkFrontend.interfaces.ITicketUpdater;
import checkFrontend.interfaces.ITicketValidator;
import global.interfaces.INetworkStatusUpdate;
import global.model.TicketModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TicketValidation implements ITicketValidator, INetworkStatusUpdate {
    private final ITicketValidator ticketCollection;
    private  TicketUpdater ticketUpdater;
    private final ITicketCommunicator websocketCommunicator;
    private ITicketCommunicator restCommunicator;
    private final INetworkStatusUpdate network;



    private boolean beenOffline;

    public TicketValidation(INetworkStatusUpdate network) {
        this.network = network;
        this.ticketCollection = new ValidTicketCollection();

        WebSocketTicketCommunicator communicator = new WebSocketTicketCommunicator(this.ticketCollection, this);
        this.websocketCommunicator = communicator;
        communicator.start(); //yep it's a thread

        this.ticketUpdater = new TicketUpdater(this.websocketCommunicator);
        this.ticketUpdater.start();

        startDump();
    }

    private void startDump(){
        RestTicketCommunicator rest = new RestTicketCommunicator(this.ticketCollection, this);
        this.restCommunicator = rest; //prevent garbage collector
        rest.start(); //yep it's a thread
    }

    public void reset() {
        ticketCollection.reset();
    }

    public TicketResultModel validate(String number) {
        TicketResultModel model = this.ticketCollection.validate(number);
        TicketModel ticket = TicketModel.ParseByTicketNumber(number);

        if(ticket != null && model.getStatus() == TicketStatus.VALID){
            ticket.setAmountChecked(ticket.getAmountChecked() + 1);
            model.setCount(model.getCount()+1);
            updateTicket(ticket);
        }

        if(model.getStatus() == TicketStatus.INVALID){

            if(ticket == null || ticket.getDate().before(websocketCommunicator.getLastUpdateDate())){
                model = new TicketResultModel();
                model.setStatus(TicketStatus.INVALID);
                return model;
            }

            long diff = Math.abs( new Date().getTime() - ticket.getDate().getTime() );
            if(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) > 15){
                model = new TicketResultModel();
                model.setStatus(TicketStatus.INVALID);
                return model;
            }

            model = new TicketResultModel();
            model.setStatus(TicketStatus.UNKNOWN);
        }

        return model;
    }

    public boolean updateTicket(TicketModel ticket) {
        this.ticketCollection.updateTicket(ticket);
        this.ticketUpdater.updateTicket(ticket);

        return true;
    }

    public synchronized void online() {
        network.online();
        if(beenOffline){
            this.ticketCollection.reset();
            startDump();
            beenOffline = false;
        }
    }

    public synchronized void offline(int amount) {
        network.offline(this.ticketUpdater == null ? 0: this.ticketUpdater.getQueueLength());
        beenOffline = true;
    }
}
