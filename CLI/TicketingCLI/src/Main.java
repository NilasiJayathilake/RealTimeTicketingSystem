
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;


public class Main {
    static Configuration loadc = new Configuration();
    public static void main(String[] args) throws IOException {
        int choice;
        int noOfVendors = 0;
        ArrayList<Integer> assignedNoOfTickets = new ArrayList<>(noOfVendors);
        Configuration configuration = null;
        System.out.println("Welcome to the Ticketing System!");
        Scanner s=new Scanner(System.in);
        System.out.println("Enter 1 to enter a new Event Configuration\n" +
                "Enter 2 to run with the same configuration\n");
        choice = s.nextInt();
        switch (choice){
            case 1:
                System.out.println("Enter new configurations please.\n" +
                        "No of total tickets: ");
                int noOfTickets = s.nextInt();
                System.out.println("Max Ticket Capacity: ");
                int maxTicketCapacity = s.nextInt();
                System.out.println("Ticket Rate: ");
                double ticketRate = s.nextDouble();
                System.out.println("Ticket Release Rate from Vendors: ");
                int releaseRate = s.nextInt();
                System.out.println("Ticket Retrieval Rate of Customers: ");
                int retrievalRate = s.nextInt();
                System.out.println("Please Enter the Amount Of Vendors for the Event: ");
                noOfVendors = s.nextInt();
                System.out.println("Assign the No of Tickets to Each Vendor: ");
                for (int i = 1; i <= noOfVendors; i++){
                    System.out.println("Vendor "+(i)+": ");
                    int assigned = s.nextInt();
                    assignedNoOfTickets.add(assigned);
                }
                Configuration c=new Configuration(noOfTickets, maxTicketCapacity, ticketRate, releaseRate, retrievalRate, noOfVendors, assignedNoOfTickets);
                try {
                    c.saveConfiguration();
                    System.out.println(c.saveConfiguration());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                configuration = c.loadConfiguration();

                break;
            case 2:
                System.out.println("Running the System with Existing Configurations");
//                System.out.println(loadc.toString());
                configuration = loadc.loadConfiguration();
                break;
            case 3:
                System.out.println("Thank You!");
                break;
            default:
                System.out.println("Invalid Choice, Please enter a Valid input");
                return;

            }
        System.out.println("Starting the Simulation: ");
        Event event = new Event(1, "La Foresta", "Ahangama");
        TicketPool ticketPool=new TicketPool(configuration.getMaxTicketCapacity());

        Management management = new Management(ticketPool, configuration, event, configuration.getNoOfVendors(), configuration.getAssignedNoOfTickets());
        management.startSimulation();
        }
}