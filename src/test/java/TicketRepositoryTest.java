import ContextMock.UnitTestTicketContext;
import global.model.TicketModel;
import org.junit.Assert;
import org.junit.Test;
import ticketManager.databaseAccessLayer.ITicketContext;
import ticketManager.exceptions.TicketDuplicateException;
import ticketManager.logic.TicketRepository;

import java.sql.SQLException;

public class TicketRepositoryTest {

    private UnitTestTicketContext lastContext;
    private TicketRepository getRepository(){
        lastContext = new UnitTestTicketContext();
        return new TicketRepository(lastContext);
    }

    @Test
    public void increaseAmountAndGetById(){
        getRepository().increaseAmountAndGetById("blbla");
        Assert.assertEquals("blbla", lastContext.lastIncreaseId);
    }

    @Test
    public void getAllAfterId() {

        getRepository().getAllAfterId(1);
        Assert.assertEquals(1, lastContext.lastAllAfterId);
    }

    @Test
    public void getAllValidAfterId(){
        getRepository().getAllValidAfterId(2);
        Assert.assertEquals(2, lastContext.lastAllValidAfterId);
    }

    @Test
    public void newTicket() throws TicketDuplicateException {
        TicketModel ticket = TicketModel.ParseByTicketNumber("162009117008919");
        getRepository().newTicket(ticket);

        Assert.assertNotNull(ticket);
        Assert.assertEquals(ticket.getTicketNumber(),  lastContext.lastCreateTicket.getTicketNumber());
    }
}
