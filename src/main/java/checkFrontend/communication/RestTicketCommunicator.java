package checkFrontend.communication;

import checkFrontend.interfaces.ITicketCommunicator;
import checkFrontend.interfaces.ITicketValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.interfaces.INetworkStatusUpdate;
import global.model.TicketExternalCommunicationModel;
import global.model.TicketModel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);

            // add header
            get.setHeader("Authorization", "Basic Y2FybG86cGFzc3dvcmQ=");

            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(get);
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Response Code : " +
                        response.getStatusLine().getStatusCode());


                BufferedReader rd;
                rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                TicketExternalCommunicationModel[] tickets = getGson().fromJson(result.toString(), TicketExternalCommunicationModel[].class);
                httpClient.close();
                this.network.online();

                handleTickets(tickets);
                return;
            } catch (IOException e) {
//                e.printStackTrace();
                this.network.offline(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
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
