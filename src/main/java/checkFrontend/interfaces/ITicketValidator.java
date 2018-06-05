package checkFrontend.interfaces;

import checkFrontend.TicketResultModel;

public interface ITicketValidator extends ITicketUpdater {
    void reset();
    TicketResultModel validate(String number);
}
