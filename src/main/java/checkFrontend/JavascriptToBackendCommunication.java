package checkFrontend;

import checkFrontend.interfaces.ITicketValidator;
import checkFrontend.ticketValidation.TicketValidation;

public class JavascriptToBackendCommunication {


    private final ITicketValidator validator;

    public JavascriptToBackendCommunication(ITicketValidator validator) {
        this.validator = validator;
    }

    public int scanTicket(String ticket){
        try {
            TicketResultModel validation = this.validator.validate(ticket);
            switch (validation.getStatus()){
                case INVALID:
                    return -1;
                case UNKNOWN:
                    return -2;
                case VALID:
                    return validation.getCount();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -2;
    }
}
