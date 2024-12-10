package com.ticketingplatform.ticketing_platform.Controller;

import com.ticketingplatform.ticketing_platform.Models.Configuration;
import com.ticketingplatform.ticketing_platform.Service.ConfigurationService;
import com.ticketingplatform.ticketing_platform.Models.Logger;
import com.ticketingplatform.ticketing_platform.Service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// REST controller for ticket operations
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api")
public class TicketController {
    private final TicketService ticketService;
    private final ConfigurationService configurationService;

    public TicketController(TicketService ticketService, ConfigurationService configurationService) {
        this.ticketService = ticketService;
        this.configurationService = configurationService;
    }

    // Endpoint to retrieve a ticket for a customer
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/retrieve")
    public ResponseEntity<Map<String, Object>> retrieveTicket(@RequestBody Map<String, String> request) {
        String customerName = request.get("customerName");
        String eventName = request.get("eventName");

        if (customerName == null || eventName == null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "Customer Name and Event Name are required")
            );
        }

        // Fetch the event configuration
        Configuration eventConfig = configurationService.findByEventName(eventName);

        if (eventConfig == null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "Event not found")
            );
        }

        synchronized (this) {
            // Check if tickets are available
            if (eventConfig.getTotalTickets() > 0) {
                // Decrement total tickets
                eventConfig.setTotalTickets(eventConfig.getTotalTickets() - 1);
                configurationService.saveConfiguration(eventConfig);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Ticket reserved successfully");
                response.put("remainingTickets", eventConfig.getTotalTickets());

                Logger.logInfo("Ticket reserved successfully");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(
                        Collections.singletonMap("message", "No tickets available")
                );
            }
        }
    }


    // Endpoint to release tickets into the system by a vendor
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/release")
    public ResponseEntity<String> releaseTicket (@RequestParam int vendorId ,@RequestParam int ticketsPerRelease) {
        boolean success = ticketService.releaseTicket (vendorId, ticketsPerRelease);
        if (success) {
            return ResponseEntity.ok("Tickets released");
        } else {
            return ResponseEntity.badRequest().body("Failed to release tickets");
        }
    }

    // Endpoint to stop the ticketing system
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem(){
        ticketService.stopSystem();
        Logger.logInfo("System stopped");
        return ResponseEntity.ok("System stopped");

    }

}