import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Management {
    private final TicketPool ticketPool;
    private final Configuration configuration;
    private final Event event;
    private int noOfVendors;
    ArrayList<Integer> assignedNoOfTickets;

    private final ExecutorService vendorExecutor;
    private final ExecutorService customerExecutor;

    public Management(TicketPool ticketPool, Configuration configuration, Event event, int noOfVendors, ArrayList<Integer> assignedNoOfTickets ) {
        this.ticketPool = ticketPool;
        this.configuration = configuration;
        this.event = event;
        this.noOfVendors=noOfVendors;
        this.assignedNoOfTickets = new ArrayList<>(assignedNoOfTickets);
        System.out.println(this.noOfVendors);

        this.vendorExecutor =Executors.newFixedThreadPool(noOfVendors);
        this.customerExecutor=Executors.newCachedThreadPool();
    }



    // Method to start vendors and customers
    public void startSimulation() {


        for (int i = 0; i < noOfVendors; i++) {
                Vendor vendor = new Vendor(i, assignedNoOfTickets.get(i), configuration.getReleaseRate(), configuration.getTicketRate(), ticketPool, event);
                vendorExecutor.submit(vendor);

        }

        for (int i = 0; i < configuration.getRetrievalRate()*5; i++) {
            Customer customer = new Customer(i, "Customer " + i, ticketPool, configuration.getRetrievalRate());
            customerExecutor.submit(customer);
        }

    }


   //      Shutdown executors after tasks complete


    }


