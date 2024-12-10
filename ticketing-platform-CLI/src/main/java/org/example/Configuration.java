package org.example;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Configuration class
public class Configuration {
    private Integer totalTickets;
    private Integer ticketReleaseRate;
    private Integer customerRetrievalRate;
    private Integer maxTicketCapacity;
    private String eventName;
    private Integer vendorCount;
    private Integer customerCount;

    // Default constructor
    public Configuration() {
    }

    // Constructor with parameter
    public Configuration(Integer totalTickets, Integer ticketReleaseRate, Integer customerRetrievalRate, Integer maxTicketCapacity, String eventName, Integer vendorCount, Integer customerCount) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.eventName = eventName;
        this.customerCount = customerCount;
        this.vendorCount = vendorCount;
    }

    // Getters and Setters
    public Integer getTotalTickets() {
        return this.totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public void setTicketReleaseRate(Integer ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public Integer getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(Integer customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public Integer getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    public void setMaxTicketCapacity(Integer maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getCustomerCount() {
        return this.customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getVendorCount() {
        return this.vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    // Method to save the configuration to JSON file
    public boolean saveConfiguration(Configuration configuration) {
        Gson gson = new Gson();
        //File file = new File("/Users/sanhindajayarathne/Desktop/your_file.json");
        File file = new File("./file.json");

        try (FileWriter writer = new FileWriter(file, true)) {
            // Convert configuration object to JSON
            String json = gson.toJson(configuration);
            writer.write(json);
            writer.write(System.lineSeparator());
            writer.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
            return false;
        }
    }
}
