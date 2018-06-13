package issueFrontend;

import issueFrontend.Calculation.GetSalesCalculator;
import issueFrontend.ClientTicketCreator;
public class JavascriptToBackendCommunication {

    private ClientTicketCreator creator;
    public JavascriptToBackendCommunication(ClientTicketCreator clientTicketCreator) {
        creator = clientTicketCreator;
    }

    public String newTicket(){
        return creator.generateNewTicket().getTicketNumber();
    }
    public String getStats(){
        GetSalesCalculator calculator = new GetSalesCalculator();
        return calculator.Result();
    }
}
