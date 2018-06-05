package checkFrontend.ticketValidation;

import checkFrontend.interfaces.ITicketCommunicator;
import checkFrontend.interfaces.ITicketUpdater;
import global.model.TicketModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketUpdater extends Thread implements ITicketUpdater{
    private final BlockingQueue<TicketModel> models = new LinkedBlockingQueue<TicketModel>();
    private final ITicketCommunicator communicator;
    private volatile boolean working;
    public TicketUpdater(ITicketCommunicator communicator) {
        this.communicator = communicator;
    }

    public int getQueueLength(){
        return this.models.size() + (working ? 1 : 0);
    }
    public boolean updateTicket(TicketModel ticket) {
        try {
            models.put(ticket);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            try {
                working = false;
                update( models.take() );
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    private void update(TicketModel model) throws InterruptedException {
        working = true;
        while ( !this.communicator.updateTicket(model)){
            Thread.sleep(1000);
        }
    }

}
