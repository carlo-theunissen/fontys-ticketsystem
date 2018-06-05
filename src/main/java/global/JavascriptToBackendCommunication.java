package global;

import issueFrontend.ClientTicketCreator;
import netscape.javascript.JSObject;

public class JavascriptToBackendCommunication {

    private ClientTicketCreator creator;
    public JavascriptToBackendCommunication(ClientTicketCreator clientTicketCreator) {
        creator = clientTicketCreator;
    }

    public String newTicket(){
        return creator.generateNewTicket().getTicketNumber();
    }
}
