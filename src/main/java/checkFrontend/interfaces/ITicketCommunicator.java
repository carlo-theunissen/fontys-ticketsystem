package checkFrontend.interfaces;

import java.util.Date;

public interface ITicketCommunicator extends ITicketUpdater {
    Date getLastUpdateDate();
}
