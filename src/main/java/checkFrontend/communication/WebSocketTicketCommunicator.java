package checkFrontend.communication;

import checkFrontend.interfaces.ITicketCommunicator;
import checkFrontend.interfaces.ITicketValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.interfaces.INetworkStatusUpdate;
import global.model.TicketExternalCommunicationModel;
import global.model.TicketModel;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WebSocketTicketCommunicator extends Thread implements ITicketCommunicator {
    private final ITicketValidator validator;
    private Date lastUpdate;
    private Session session;
    private final INetworkStatusUpdate network;
    private WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    public WebSocketTicketCommunicator(ITicketValidator validator, INetworkStatusUpdate network) {
        this.validator = validator;
        this.network = network;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20);
        this.lastUpdate = cal.getTime();

    }

    private void connectToServer(){
        URI uri = URI.create("ws://localhost:8080/ws/");

        while (true) {
            try {
                session = container.connectToServer(new EventClientSocket(), uri);

                session.getBasicRemote().sendText("password");
                return;
            } catch (DeploymentException e) {
                //e.printStackTrace();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println("retry");
            network.offline(0);
        }
    }

    public Date getLastUpdateDate() {
        return lastUpdate;
    }

    @Override
    public void run() {
        super.run();
        connectToServer();
    }

    private Gson getGson(){
        return new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
    }
    public synchronized boolean updateTicket(TicketModel ticket) {
        TicketExternalCommunicationModel ticketExternal = new TicketExternalCommunicationModel(ticket);
        try {
            String text = getGson().toJson(ticketExternal);
            System.out.println("SEND: "+text);
            session.getBasicRemote().sendText(text);
        } catch (IOException ignored) {
            return false;
        }
        return true;
    }
    private synchronized void newServerUpdate(String data){
        lastUpdate = new Date();
        TicketExternalCommunicationModel ticketExternal = getGson().fromJson(data, TicketExternalCommunicationModel.class);
        validator.updateTicket(ticketExternal.toTicketModel());
    }

    @ClientEndpoint
    public class EventClientSocket{
        @OnOpen
        public void onWebSocketConnect() {
            System.out.println("[Connected]");
            network.online();
        }
        @OnMessage
        public void onWebSocketText(String message) {
            System.out.println("[Received]: " + message);
            newServerUpdate(message);
        }
        @OnClose
        public void onWebSocketClose(CloseReason reason) {
            System.out.println("[Closed]: " + reason);
            connectToServer();
        }
        @OnError
        public void onWebSocketError(Throwable cause) {

            System.out.println("[ERROR]: " + cause.getMessage());
        }
    }
}
