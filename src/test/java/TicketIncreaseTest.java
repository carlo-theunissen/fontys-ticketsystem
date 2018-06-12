import ContextMock.UnitTestMutationManager;
import ContextMock.UnitTestTicketContext;
import global.model.TicketMutationModel;
import org.junit.Assert;
import org.junit.Test;
import ticketManager.logic.TicketIncreaseProvider;
import ticketManager.logic.TicketRepository;

public class TicketIncreaseTest {
    @Test
    public void basicTest(){
        UnitTestTicketContext context = new UnitTestTicketContext();
        UnitTestMutationManager mutation = new UnitTestMutationManager();
        TicketIncreaseProvider provider = new TicketIncreaseProvider(mutation, new TicketRepository( context));
        TicketMutationModel model = provider.getAndIncrease("1");

        Assert.assertEquals("1", context.lastIncreaseId);
        Assert.assertEquals(model, mutation.getLastCreateUpdateTicketMutation());
    }
}
