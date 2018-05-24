package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketModel {
    private int id;
    private int amountChecked;
    private Date date;

    public int getId() {
        return id;
    }
    public int getTicketNumber(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt( formatter.format(date) + String.valueOf(id));
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmountChecked() {
        return amountChecked;
    }

    public void setAmountChecked(int amountChecked) {
        this.amountChecked = amountChecked;
    }

    public static TicketModel CreateNowTicketModel(){
        TicketModel ticket = new TicketModel();
        ticket.setDate(new Date());
        return ticket;
    }
}
