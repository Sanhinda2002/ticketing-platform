package org.example;

// Customer class
public class Customer implements Runnable {
    private String customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;
    public boolean running;

    // Constructor to initialize customer
    public Customer(String customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    // Getters and Setters

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRetrievalInterval() {
        return this.retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public TicketPool getTicketPool() {
        return this.ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public boolean retrieveTicket(TicketPool pool) {
        return pool.removeTicket();
    }

    // The run method
    public void run() {
        this.running = true;

        while(this.running) {
            try {
                Thread.sleep((long)this.retrievalInterval);
                if (!this.running) {
                    break;
                }

                if (this.ticketPool.removeTicket()) {
                    System.out.println("Customer" + this.customerId + "successfully retrieved a ticket");
                    Logger.logInfo("Customer" + this.customerId + "successfully retrieved a ticket");
                }
            } catch (InterruptedException e) {
                System.out.println("Customer" + this.customerId + "failed to retrieve a ticket");
            }
        }

    }

    // The stop method
    public void stop() {
        this.running = false;
    }
}

