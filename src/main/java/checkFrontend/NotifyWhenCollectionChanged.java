package checkFrontend;

import checkFrontend.interfaces.ICheckCommunication;
import checkFrontend.ticketValidation.ValidTicketCollection;

import java.util.Observable;
import java.util.Observer;

public class NotifyWhenCollectionChanged implements Observer {
    private final ICheckCommunication network;
    private final ValidTicketCollection provider;
    public NotifyWhenCollectionChanged(ICheckCommunication network, ValidTicketCollection provider) {
        this.network = network;
        this.provider = provider;
        this.provider.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        this.network.ticketAmountChanged(provider.getTotalTickets());
    }
}
