package model;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketModel {
    private int id;
    private int amountChecked;
    private Date date;
    private int randomId;

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
        return new BigInteger(formatter.format(date) + String.valueOf(randomId)).multiply(new BigInteger( String.valueOf(randomId).substring(0, 1))).toString();
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

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }
}
