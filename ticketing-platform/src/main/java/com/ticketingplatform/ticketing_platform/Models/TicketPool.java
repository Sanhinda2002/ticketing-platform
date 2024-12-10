package com.ticketingplatform.ticketing_platform.Models;

import org.springframework.stereotype.Component;
import java.util.*;

// Ticket pool class
@Component
public class TicketPool {
    private List<Integer> tickets;
    private int maxTickets;

    // Default constructor for Spring
    public TicketPool() {
        this.maxTickets = getMaxTickets();
        this.tickets = Collections.synchronizedList(new LinkedList<>());
    }

    // Constructor with a specified max ticket capacity
    public TicketPool(Integer maxTickets) {
        this.maxTickets = maxTickets;
        this.tickets = Collections.synchronizedList(new LinkedList<>());
    }

    // Getter for the maximum tickets
    public int getMaxTickets() {
        return maxTickets;
    }

    // Synchronized method for add ticket
    public synchronized void addTickets(Integer numTickets) {
        if (tickets.size() + numTickets <= maxTickets) {
            for (int i = 0; i < numTickets; i++) {
                tickets.add(1);
            }
            System.out.println("Added " + numTickets + " tickets to the pool.");
        } else {
            System.out.println("Cannot add tickets as the maximum capacity has been reached");
        }
    }

    // Synchronized method for remove ticket
    public synchronized boolean removeTicket() {
        if (!tickets.isEmpty()) {
            tickets.remove(0);
            return true;
        } else {
            System.out.println("No tickets left in the pool.");
            return false;
        }
    }

    public int getAvailableTickets() {
        return tickets.size();
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }
}

