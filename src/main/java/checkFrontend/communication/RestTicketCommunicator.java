package checkFrontend.communication;

import checkFrontend.interfaces.ITicketCommunicator;
import checkFrontend.interfaces.ITicketValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.RestHelper;
import global.interfaces.INetworkStatusUpdate;
import global.model.TicketExternalCommunicationModel;
import global.model.TicketModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.websocket.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class RestTicketCommunicator extends Thread implements ITicketCommunicator {
    private final ITicketValidator validator;
    private Date lastUpdate;
    private Session session;
    private final static String url = "http://localhost:8080/api/ticket/dump";
    private final INetworkStatusUpdate network;

    public RestTicketCommunicator(ITicketValidator validator, INetworkStatusUpdate network) {
        this.validator = validator;
        this.lastUpdate = new Date();
        this.network = network;
    }

    private void getDump(){
        while (true) {
            try {
                String result = RestHelper.getHttp(url);
                TicketExternalCommunicationModel[] tickets = getGson().fromJson(result, TicketExternalCommunicationModel[].class);
                this.network.online();
                handleTickets(tickets);
                return;
            } catch (IOException e) {
                this.network.offline(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private void handleTickets(TicketExternalCommunicationModel[] tickets){
        for (TicketExternalCommunicationModel ticket : tickets) {
            validator.updateTicket(ticket.toTicketModel());
        }
    }

    public Date getLastUpdateDate() {
        return lastUpdate;
    }

    @Override
    public void run() {
        super.run();
        getDump();

    }

    private Gson getGson(){
        return new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
    }
    public boolean updateTicket(TicketModel ticket) {
        throw new NotImplementedException();
    }
}
