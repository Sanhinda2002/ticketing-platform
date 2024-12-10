package com.ticketingplatform.ticketing_platform.Service;

import com.ticketingplatform.ticketing_platform.Models.Configuration;
import com.ticketingplatform.ticketing_platform.Repository.ConfigurationRepository;
import com.ticketingplatform.ticketing_platform.Models.Logger;
import com.ticketingplatform.ticketing_platform.Models.TicketPool;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Service class for handling operations related to tickets
@Service
public class TicketService {
    private final TicketPool ticketPool;
    private ExecutorService executorService;
    private final ConfigurationService configurationService;
    private final ConfigurationRepository configurationRepository;

    @Autowired
    // Constructor-based dependency injection
    public TicketService(TicketPool ticketPool, ConfigurationService configurationService, ConfigurationRepository configurationRepository) {
        this.ticketPool = ticketPool;
        this.configurationService = configurationService;
        this.configurationRepository = configurationRepository;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Transactional
    public synchronized boolean retrieveTicket(int customerId, String eventName) {
        // Find the specific configuration for the event
        Configuration eventConfig = configurationRepository.findFirstByEventName(eventName);

        if (eventConfig == null) {
            System.err.println("Event not found!");
            return false;
        }

        // Check if tickets are available
        if (eventConfig.getTotalTickets() <= 0) {
            // Remove the event from the database when no tickets are left
            configurationRepository.delete(eventConfig);
            return false;
        }

        // Attempt to remove ticket from pool
        boolean success = ticketPool.removeTicket();
        if (success) {
            // Update the total tickets in configuration
            int currentTickets = eventConfig.getTotalTickets();
            eventConfig.setTotalTickets(currentTickets - 1);

            try {
                // Save the updated configuration
                configurationRepository.save(eventConfig);

                // Log the reservation
                try (FileWriter writer = new FileWriter("reservations.txt", true)) {
                    writer.write("Customer ID: " + customerId + " - Event: " + eventName + "\n");
                }
                return true;
            } catch (Exception e) {
                System.err.println("Failed to update configuration: " + e.getMessage());
                // If we fail to save the configuration, add the ticket back to the pool
                ticketPool.addTickets(1);
                return false;
            }
        }

        return false;
    }

    // Method to release tickets
    public boolean releaseTicket(int vendorId, int ticketsPerRelease) {
        // Get current configuration
        Configuration[] configs = configurationService.loadConfiguration();
        if (configs.length == 0) {
            System.err.println("No configuration found");
            return false;
        }

        Configuration currentConfig = configs[0];

        if (ticketsPerRelease <= 0 || ticketsPerRelease > currentConfig.getMaxTicketCapacity()) {
            System.err.println("Invalid number of tickets to release");
            return false;
        }

        // Update configuration first
        currentConfig.setTotalTickets(currentConfig.getTotalTickets() + ticketsPerRelease);
        boolean saved = configurationService.saveConfiguration(currentConfig);

        if (saved) {
            // If configuration was updated successfully, add tickets to pool
            ticketPool.addTickets(ticketsPerRelease);
            System.out.println("Vendor " + vendorId + " released " + ticketsPerRelease + " tickets.");
            Logger.logInfo(vendorId + ": Added " + ticketsPerRelease +
                    " tickets ");
            return true;
        } else {
            System.err.println("Failed to release tickets");
            Logger.logError("Failed to release tickets");
            return false;
        }
    }

    // Stops the system by shutting down all active threads
    public void stopSystem() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            System.out.println("System stopped.");
        }
    }
}