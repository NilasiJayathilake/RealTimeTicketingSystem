import java.util.LinkedList;
import java.util.Queue;

// This is where Tickets are Added
public class TicketPool {
    // Using a Concurrent Linked Queue which is thread safe, and also dynamic
    private Queue<Ticket> tickets;
    private int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.tickets = new LinkedList<>();
    }

    /* Adding A Ticket To the Pool
       Called by the Vendor
     */
    public synchronized void addTicket(Ticket ticket) {
        try {
            // Wait if the ticket pool is at maximum capacity
            while (tickets.size() >= maxTicketCapacity) {
                System.out.println("Ticket Pool Capacity Reached. Waiting to release more tickets...");
                System.out.println("Current Pool Size: " + tickets.size());
                wait(); // Wait till customers buy tickets
            }
            // Add the ticket to the pool
            if (tickets.size()<maxTicketCapacity){
            tickets.add(ticket);
            notifyAll(); // Notify all waiting threads that a ticket was added
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted while waiting to add a ticket: " + e.getMessage());
        }
    }

    /*
        Removing a Ticket from the Pool
        Called by the Customer
     */
    private int waitingThreads;
    public synchronized Ticket removeTicket(){
            try {
                while (tickets.isEmpty()) {
                    wait();
            }} catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }

        Ticket ticket = tickets.poll();

        notifyAll(); // to notify all threads tickets have been bought
        System.out.println(ticket+" Has Been Purchased!");
        return ticket;
    }
    // method to check if Ticket Pool is empty
    public synchronized boolean isPoolEmpty(){
        return tickets.isEmpty();
    }
    public synchronized boolean isTicketAvailable(Ticket ticket){
        return  tickets.contains(ticket);
    }

    public int PoolSize(){
        return tickets.size();
    }


}
