package ticketManager.json;

public class TicketUpdateMessage {
    public String ticketNumber;
    public int count;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public int getCount() {
        return count;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
