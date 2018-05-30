package ticketManager.model;

import javax.websocket.Session;
import java.util.Observable;

public class WebSocketSessionModel extends Observable {
    private final Session session;

    public WebSocketSessionModel(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
    public void Closed(){
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebSocketSessionModel that = (WebSocketSessionModel) o;
        return session.equals(that.session);
    }

    @Override
    public int hashCode() {

        return session.hashCode();
    }
}
