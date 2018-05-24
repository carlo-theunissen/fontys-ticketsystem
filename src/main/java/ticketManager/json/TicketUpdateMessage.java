package ticketManager.json;

public class TicketUpdateMessage {
    public int ticketNumber;
    public int count;

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getCount() {
        return count;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
