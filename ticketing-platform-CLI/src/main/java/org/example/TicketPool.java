package org.example;

import java.util.LinkedList;
import java.util.Queue;

// Ticket pool class
public class TicketPool {
    private final int maxTickets;
    private final Queue<Integer> tickets;
    private boolean vendorsStopped = false;

    // Constructor to initialize the ticket pool
    public TicketPool(int maxTickets) {
        this.maxTickets = maxTickets;
        this.tickets = new LinkedList();
        Logger.logInfo("TicketPool initialized with max capacity: " + maxTickets);
    }

    // Synchronized method to add tickets
    public synchronized void addTickets(int numTickets) {
        String threadName = Thread.currentThread().getName();
        if (this.tickets.size() >= this.maxTickets) {
            this.vendorsStopped = true;
        } else {
            int availableSpace = this.maxTickets - this.tickets.size();
            int ticketsToAdd = Math.min(numTickets, availableSpace);

            for(int i = 0; i < ticketsToAdd; ++i) {
                this.tickets.add(1);
            }

            Logger.logInfo(threadName + ": Added " + ticketsToAdd + " tickets. Current pool size: " + this.tickets.size());
            this.notifyAll();
        }
    }

    // Synchronized method to remove tickets
    public synchronized boolean removeTicket() {
        String threadName = Thread.currentThread().getName();

        while(this.tickets.isEmpty() && !this.vendorsStopped) {
            try {
                this.wait();
            } catch (InterruptedException var3) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        if (this.tickets.isEmpty() && this.vendorsStopped) {
            return false;
        } else {
            this.tickets.remove();
            Logger.logInfo(threadName + ": Ticket removed. Remaining tickets: " + this.tickets.size());
            System.out.println(threadName + ": Ticket removed. Remaining tickets: " + this.tickets.size());
            this.notifyAll();
            return true;
        }
    }

    // Method to return the current number of available tickets in the pool
    public synchronized int getAvailableTickets() {
        return this.tickets.size();
    }

    // Method to return the maximum ticket capacity of the pool
    public synchronized int getMaxTickets() {
        return this.maxTickets;
    }

    // Method to return, whether vendors stopped
    public synchronized boolean isExhausted() {
        return this.tickets.isEmpty() && this.vendorsStopped;
    }

    // Method to return vendors as stopped
    public synchronized void stop() {
        this.vendorsStopped = true;
        this.notifyAll();
    }
}

