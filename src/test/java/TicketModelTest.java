import global.model.TicketModel;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TicketModelTest {
    @Test
    public void BasicTicketNumberGeneration() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,1);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2018);

        TicketModel ticket = new TicketModel();
        ticket.setDate(cal.getTime());
        ticket.setRandomId(99);

        Assert.assertEquals("162009117008919", ticket.getTicketNumber());
        //18001013000991
        //162009117008919
    }

    @Test
    public void BasicTicketFromNumber(){
        TicketModel ticket = TicketModel.ParseByTicketNumber("162009117008919");
        Assert.assertNotNull( ticket);
        Assert.assertEquals("162009117008919", ticket.getTicketNumber());
    }

    @Test
    public void BasicTicket2FromNumber(){
        TicketModel ticket = TicketModel.ParseByTicketNumber("908156717477155");
        Assert.assertNotNull( ticket);
        Assert.assertEquals("908156717477155", ticket.getTicketNumber());
    }
}