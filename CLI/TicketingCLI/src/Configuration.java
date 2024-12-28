import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Configuration {
    private int noOfTickets;
    private int maxTicketCapacity;
    private double ticketRate;
    private int releaseRate;
    private int retrievalRate;
    private int noOfVendors;
    private ArrayList<Integer> assignedNoOfTickets = new ArrayList<>();
    public Configuration() {
    }


    public Configuration(int noOfTickets, int maxTicketCapacity, double ticketRate, int releaseRate, int retrievalRate, int noOfVendors, ArrayList <Integer> assignedNoOfTickets) {
        this.noOfTickets = noOfTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketRate = ticketRate;
        this.releaseRate = releaseRate;
        this.retrievalRate = retrievalRate;
        this.noOfVendors = noOfVendors;
        this.assignedNoOfTickets =assignedNoOfTickets;
    }
    public String saveConfiguration() throws IOException {
        File file = new File("TicketConfiguration.json");
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        String json = gson.toJson(this);
        FileWriter fw = new FileWriter(file);
        try {
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fw.close();
        return "Configurations Have Been Saved at: " + file;
    }

    public Configuration loadConfiguration() throws IOException{
        File file = new File("TicketConfiguration.json");
        if (file.exists()){
            System.out.println("Configurations Exist: Retrieval Successful");
            //to make sure gson knows what is the type of the class it should deserialize to passing Configurations.class
        }else {
            System.out.println("File Does Not Exist");
        }

        try (FileReader reader = new FileReader("TicketConfiguration.json")) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);
            System.out.println("Loaded Configuration: " + config);
            System.out.println("Loaded noOfVendors: " + config.getNoOfVendors());
            return config;
        } catch (Exception e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
    public String toString() {
        return "Configuration{" +
                "noOfTickets=" + noOfTickets +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", ticketRate=" + ticketRate +
                ", releaseRate=" + releaseRate +
                ", retrievalRate=" + retrievalRate +
                ", noOfVendors=" + noOfVendors +
                ", assignedNoOfTickets=" + assignedNoOfTickets +
                '}';
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public double getTicketRate() {
        return ticketRate;
    }

    public void setTicketRate(double ticketRate) {
        this.ticketRate = ticketRate;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public int getNoOfVendors() {
        return noOfVendors;
    }

    public void setNoOfVendors(int noOfVendors) {
        this.noOfVendors = noOfVendors;
    }

    public ArrayList<Integer> getAssignedNoOfTickets() {
        return assignedNoOfTickets;
    }

    public void setAssignedNoOfTickets(ArrayList<Integer> assignedNoOfTickets) {
        this.assignedNoOfTickets = assignedNoOfTickets;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }
}
