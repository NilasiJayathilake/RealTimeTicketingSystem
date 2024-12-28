import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Vendor implements Runnable {
    private int vendorId;
    private int releaseRate; // the no of tickets a vendor releases to the pool at a time
    private double ticketRate;
    private final TicketPool ticketPool;
    private Event event; // Vendor has events
    private int assignedNoOfTickets;
    private Customer customer;



    public Vendor(int vendorId,int assignedNoOfTickets, int releaseRate,double ticketRate, TicketPool ticketPool, Event event) {
        this.vendorId=vendorId;
        this.assignedNoOfTickets=assignedNoOfTickets;
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
        this.event = event;
        this.ticketRate=ticketRate;
    }

    /*
        In the Run method, the Task that needs to be done using threads (Add Tickets) is called.
        Every Vendor is a Thread
        Each thread tries to access the TicketPool to add tickets.
        Each vendor is assigned a Thread in the main method using executor service.
     */
    @Override
    public void run() {
        // Threads Handle Releasing tickets to the Pool

                /* if the amount of tickets less is lesser than the release rate, it will add the remaining amount to the pool
                    Or else it will add the release rate to the pool
                 */
        int count=0;
                synchronized (ticketPool) {

                    for (int i = 0; i < assignedNoOfTickets; i++) {
                        // generating random ticket ids
                        int ticketId = TicketIdGenerator.getNextTicketId();

                        Ticket ticket = new Ticket(ticketId, ticketRate, event);
                        ticketPool.addTicket(ticket);
                        count++;
                        System.out.println("Vendor: " + Thread.currentThread().getName() + "Added Ticket No: " + ticketId
                                + "\n Pool Size Currently: " + ticketPool.PoolSize());
                    }

                }

                try {
                    Thread.sleep( 500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


        System.out.println("Vendor "+vendorId+" Finished Adding Tickets.\n" +
                " Executed by: "+Thread.currentThread().getName()+" Added Count: "+count);

    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }
}
