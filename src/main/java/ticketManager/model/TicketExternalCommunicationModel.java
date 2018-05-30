package ticketManager.model;

import model.TicketModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TicketExternalCommunicationModel {
    public final String date;
    public final int id;
    public final String ticketNumber;
    public final int amount;


    public TicketExternalCommunicationModel(TicketModel ticket) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        date =  df.format(ticket.getDate());
        id = ticket.getId();
        ticketNumber = ticket.getTicketNumber();
        amount = ticket.getAmountChecked();
    }

}
