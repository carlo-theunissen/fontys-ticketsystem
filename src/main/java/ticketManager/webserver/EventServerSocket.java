package ticketManager.webserver;

import ticketManager.model.WebSocketSessionModel;
import ticketManager.authentication.websocket.SimpleAuthenticationGuard;
import ticketManager.communication.FromClientCommunication;
import ticketManager.communication.IMessageProcessor;
import ticketManager.logic.CheckUnitBroadcaster;
import ticketManager.logic.TicketIncreaseProvider;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;


@ServerEndpoint(value = "/ws/")
public class EventServerSocket {


    private IMessageProcessor processor;

    public void setMessageProcessor(IMessageProcessor processor) {
        this.processor = processor;
    }


    private final HashMap<String, WebSocketSessionModel> sessionModels;

    public EventServerSocket() {
        sessionModels = new HashMap<String, WebSocketSessionModel>();
        SimpleAuthenticationGuard auth = EventServer.getWebSocketAuthentication();
        auth.sendValidMessageTo(new FromClientCommunication(new TicketIncreaseProvider( EventServer.getMutationManager(), EventServer.getRepository() ), new CheckUnitBroadcaster( auth )));
        setMessageProcessor(auth);
    }

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("CONNECT! " + session.getId());

    }
    @OnMessage
    public void onText(String message, Session session) {
        System.out.println(message);
        WebSocketSessionModel model;
        if(sessionModels.containsKey(session.getId())){
            model = sessionModels.get(session.getId());
        } else {
            model = getSessionModel(session);
            sessionModels.put(session.getId(), model);
        }

        processor.onMessage(message, model);

    }
    private WebSocketSessionModel getSessionModel(Session session){
       return new WebSocketSessionModel(session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[Socket Closed: " + reason.getReasonPhrase());
        if(sessionModels.containsKey(session.getId())){
            sessionModels.get(session.getId()).Closed();
            sessionModels.remove(session.getId());
        }


    }
    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }
}