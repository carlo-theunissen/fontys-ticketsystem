package global.model;

import java.math.BigInteger;
import java.text.ParseException;
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
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyDDDHHmmss");
    public String getTicketNumber(){
        //y year
        //D day of year
        //H hour in day
        //m minute in hour
        //s second in minute
        String multiply = String.valueOf(randomId).substring(0, 1);
        return new BigInteger(formatter.format(date) + String.valueOf(randomId)).multiply(new BigInteger( multiply)).toString() + multiply;
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

    public static TicketModel ParseByTicketNumber(String ticketNumber){

        try {
            if(ticketNumber.length() < 14){
                return null;
            }
            BigInteger bigInt = new BigInteger(ticketNumber);
            char multiplier = ticketNumber.charAt(ticketNumber.length() - 1);
            bigInt = bigInt.divide( new BigInteger( String.valueOf( multiplier) ));

            TicketModel model = new TicketModel();
            model.setDate( formatter.parse( bigInt.toString().substring(0, formatter.toPattern().length())));
            model.setRandomId(Integer.parseInt( bigInt.toString().substring(formatter.toPattern().length(), ticketNumber.length() - 1)));
            return model;
        } catch (ParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e){
            return null;
        } catch (NumberFormatException e){
            return null;
        }

    }

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }

    @Override
    public String toString() {
        return this.getTicketNumber();
    }
}
