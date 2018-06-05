package issueFrontend;

import global.interfaces.INetworkStatusUpdate;
import global.model.TicketModel;

public class ClientTicketCreator {
    private final ServerCommunicationThread communication;
    private Thread thread;
    public ClientTicketCreator(INetworkStatusUpdate update) {
        communication = new ServerCommunicationThread(update);
        communication.start();
    }

    public TicketModel generateNewTicket(){
        TicketModel model = TicketModel.CreateNowTicketModel();
        model.setRandomId((int) (Math.random() * 1000 + 1));
        communication.sendNewTicketToServer(model);
        return model;
    }
}
