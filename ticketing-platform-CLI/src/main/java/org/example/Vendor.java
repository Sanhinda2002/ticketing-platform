package org.example;

// Vendor class
public class Vendor implements Runnable {
    private int vendorID;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;
    private boolean running = true;

    // Constructor to initialize vendor
    public Vendor(int vendorID, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorID = vendorID;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
        Logger.logInfo("Vendor " + vendorID + " initialized");
    }

    // Getters and Setters
    public int getVendorID() {
        return this.vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    public int getTicketsPerRelease() {
        return this.ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return this.releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    public TicketPool getTicketPool() {
        return this.ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // The run method
    public void run() {
        Thread.currentThread().setName("Vendor-" + this.vendorID);

        while(this.running && !this.ticketPool.isExhausted()) {
            try {
                Thread.sleep((long)this.releaseInterval * 1000L);
                if (!this.running) {
                    break;
                }

                int ticketsToRelease = Math.min(this.ticketsPerRelease, this.ticketPool.getMaxTickets() - this.ticketPool.getAvailableTickets());
                if (ticketsToRelease > 0) {
                    this.ticketPool.addTickets(ticketsToRelease);
                    System.out.println("Vendor " + this.vendorID + " released " + ticketsToRelease + " tickets");
                }
            } catch (InterruptedException var2) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        Logger.logInfo("Vendor " + this.vendorID + " stopped");
    }

    // The stop method
    public void stop() {
        this.running = false;
        Logger.logInfo("Stop signal received for Vendor " + this.vendorID);
    }
}
