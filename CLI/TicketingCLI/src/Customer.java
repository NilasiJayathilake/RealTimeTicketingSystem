import java.util.Queue;

public class Customer implements Runnable {
    private int c_id;
    private String name;
    TicketPool ticketPool;
    int noOfTickets;
    private int retrievalRate;
    private Queue<Customer> customerQueue;

    public Customer() {}

    public Customer(int c_id, String name, TicketPool ticketPool, int retrievalRate) {
        this.c_id = c_id;
        this.name = name;
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }
    @Override
    public void run() {
        try {
        while (true) synchronized (ticketPool) {
            Ticket ticket = ticketPool.removeTicket();
            System.out.println("Purchased By: " + getName() + " Executed By: " + Thread.currentThread().getName() + "\n Current Pool Size: " + ticketPool.PoolSize());
            Thread.sleep(retrievalRate * 50L);
        }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }



    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    /*
                In the Run method, the Task that needs to be done using threads (Remove Tickets) is called.
                Each Customer is a Thread.
                Customers try to concurrently access the ticket pool and purchase tickets.
                Each Customer is assigned a Thread in the main method using executor service.
                         */
    /*
        Customer Logic:
            Check if the Ticket Pool is empty/ Tickets are available
            Check if the purchasing Ticket Exists in the Pool.
            If it does remove it from the pool
     */


}
