package global.model;

import java.util.Date;

public class TicketExternalCommunicationModel {
    private Date date;
    private int id;
    private int randomId;
    private String ticketNumber;
    private int amount;


    public TicketExternalCommunicationModel(TicketModel ticket) {
        date =  ticket.getDate();
        id = ticket.getId();
        ticketNumber = ticket.getTicketNumber();
        amount = ticket.getAmountChecked();
        randomId = ticket.getRandomId();
    }
    public TicketModel toTicketModel(){
        TicketModel model = new TicketModel();
        model.setId(id);
        model.setDate(date);
        model.setAmountChecked(amount);
        model.setRandomId(randomId);
        return model;
    }
    public TicketExternalCommunicationModel(){

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
