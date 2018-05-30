package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketModel {
    private int id;
    private int amountChecked;
    private Date date;

    public int getId() {
        return id;
    }
    public String getTicketNumber(){
        //y year
        //D day of year
        //H hour in day
        //m minute in hour
        //s second in minute
        SimpleDateFormat formatter = new SimpleDateFormat("yyDDDHHmmss");
        return  formatter.format(date) + String.valueOf(id);
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
