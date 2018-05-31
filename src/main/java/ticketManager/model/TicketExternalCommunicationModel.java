package ticketManager.model;

import model.TicketModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketExternalCommunicationModel {
    public final Date date;
    public final int id;
    public final int randomId;
    public final String ticketNumber;
    public final int amount;


    public TicketExternalCommunicationModel(TicketModel ticket) {
        date =  ticket.getDate();
        id = ticket.getId();
        ticketNumber = ticket.getTicketNumber();
        amount = ticket.getAmountChecked();
        randomId = ticket.getRandomId();
    }

}
