package com.ticketingplatform.ticketing_platform.Models;

// Customer class
public class Customer implements Runnable {
    private String customerName;
    private int retrievalInterval;
    private TicketPool ticketPool;

    // Constructor to initialize customer
    public Customer(String customerName, int retrievalInterval, TicketPool ticketPool) {
        this.customerName = customerName;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    // Getters and Setters

    public String getCustomerId() {
        return customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerName = customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public boolean retrieveTicket(TicketPool pool) {
        return pool.removeTicket();
    }

    private boolean running;

    // The run method
    @Override
    public void run() {

        running = true;

        while (running){
            try {
                Thread.sleep(retrievalInterval);

                if (!running) break;

                if (ticketPool.removeTicket()) {
                    System.out.println("Customer" + customerName +
                            "successfully retrieved a ticket");
                }

            }catch (InterruptedException e){
                System.out.println("Customer" + customerName +
                        "failed to retrieve a ticket");
            }
        }
    }
}
