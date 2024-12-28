public class Ticket {
    private int ticketId;
    private double ticketRate;
    private Event event; // Composition - 'has-a' relationship. "Event Has A Ticket"
    private Customer customer;

    public Ticket( int ticketId, double ticketRate, Event event ) {
        this.ticketId=ticketId;
        this.ticketRate=ticketRate;
        this.event = event;
    }

    @Override
    public String toString() {
        return "TicketID: "+ticketId+" For Event: "+event.getEventName();
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getTicketRate() {
        return ticketRate;
    }

    public void setTicketRate(double ticketRate) {
        this.ticketRate = ticketRate;
    }
}
