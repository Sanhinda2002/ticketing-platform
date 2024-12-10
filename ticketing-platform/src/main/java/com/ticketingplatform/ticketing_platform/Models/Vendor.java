package com.ticketingplatform.ticketing_platform.Models;

import java.lang.Runnable;

// Vendor class
public class Vendor implements Runnable{
    private int vendorID;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;

    // Constructor to initialize vendor
    public Vendor (int vendorID, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool){
        this.vendorID = vendorID;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    // Getters and Setters

    public int getVendorID() {
        return vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval(){
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    private boolean running;

    // The run method
    @Override
    public void run() {

        running = true;

        while (running){
            try{
                Thread.sleep(releaseInterval);

                if(!running) break;

                if (ticketPool.getAvailableTickets() < ticketPool.getMaxTickets()) {
                    ticketPool.addTickets(ticketsPerRelease);
                    System.out.println("Vendor " + vendorID +
                            " released" + ticketsPerRelease +
                            " tickets");
                }

            }catch (InterruptedException e){
                System.out.println("Vendor " + vendorID +
                        " can't release any tickets");
            }
        }
    }

}

