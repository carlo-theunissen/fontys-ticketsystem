package global.model;

public class TicketMutationModel {
    private final TicketModel ticket;
    private final TicketMutationType type;
    private final int id;
    public TicketMutationModel(TicketModel ticket, TicketMutationType type, int id) {
        this.ticket = ticket;
        this.type = type;
        this.id = id;
    }

    public TicketModel getTicket() {
        return ticket;
    }

    public TicketMutationType getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
