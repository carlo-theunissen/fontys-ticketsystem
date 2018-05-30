package ticketManager.authentication.websocket;

import ticketManager.model.WebSocketSessionModel;
import ticketManager.communication.IMessageProcessor;
import ticketManager.logic.IWebSocketSessionStorage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SimpleAuthenticationGuard implements IMessageProcessor, IWebSocketSessionStorage, Observer {

    private IMessageProcessor processor;
    private static final String PASSWORD = "password";
    private final ArrayList<WebSocketSessionModel> validSessions;

    public SimpleAuthenticationGuard() {
        validSessions = new ArrayList<WebSocketSessionModel>();
    }

    public void onMessage(String string, WebSocketSessionModel session) {
        if(validSessions.contains(session)){
            this.processor.onMessage(string, session);
            return;
        }
        if(string.equals(PASSWORD)){
            session.addObserver(this);
            validSessions.add(session);
        }
    }

    public void sendValidMessageTo(IMessageProcessor processor){
        this.processor = processor;
    }

    public void update(Observable o, Object arg) {
        WebSocketSessionModel model = (WebSocketSessionModel) o;
        validSessions.remove(model);
    }

    public WebSocketSessionModel[] getUnits() {
        WebSocketSessionModel[] result = new WebSocketSessionModel[validSessions.size()];
        return  validSessions.toArray(result);
    }
}
