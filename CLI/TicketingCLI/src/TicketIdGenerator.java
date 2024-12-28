import java.util.concurrent.atomic.AtomicInteger;

public class TicketIdGenerator {
    private static final AtomicInteger ticketIdCounter = new AtomicInteger(001); // Start from 100

    public static int getNextTicketId() {
        return ticketIdCounter.getAndIncrement(); // Get the current ID and increment for the next
    }
}
