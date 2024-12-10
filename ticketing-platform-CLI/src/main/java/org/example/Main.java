package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Shared ticket pool resource
    private static TicketPool ticketPool;

    // Lists to hold vendors, customers, and their respective threads
    private static List<Vendor> vendors = new ArrayList();
    private static List<Customer> customers = new ArrayList();
    private static List<Thread> vendorThreads = new ArrayList();
    private static List<Thread> customerThreads = new ArrayList();

    // Simulation is running flag
    private static boolean running = false;

    public Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger.logInfo("Ticket Simulation System Started");

        // Gather simulation details from the user
        try {
            System.out.println("Enter Event Name:");
            String eventName = scanner.nextLine();
            Logger.logInfo("Event Name: " + eventName);

            System.out.println("Enter Total Tickets:");
            int totalTickets = validate(scanner);
            Logger.logInfo("Total Tickets: " + totalTickets);

            System.out.println("Enter Maximum Ticket Capacity:");
            int maxTicketCapacity = validate(scanner);
            Logger.logInfo("Max Ticket Capacity: " + maxTicketCapacity);

            System.out.println("Enter Ticket Release Rate (seconds):");
            int ticketReleaseRate = validate(scanner);
            Logger.logInfo("Ticket Release Rate: " + ticketReleaseRate);

            System.out.println("Enter Customer Retrieval Rate (seconds):");
            int customerRetrievalRate = validate(scanner);
            Logger.logInfo("Customer Retrieval Rate: " + customerRetrievalRate);

            System.out.println("Enter Number of Vendors:");
            int numVendors = validate(scanner);
            Logger.logInfo("Number of Vendors: " + numVendors);

            System.out.println("Enter Number of Customers:");
            int numCustomers = validate(scanner);
            Logger.logInfo("Number of Customers: " + numCustomers);

            // Create a configuration object with the gathered inputs
            Configuration configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, eventName, numVendors, numCustomers);

            // Save the configuration and log success or failure
            if (configuration.saveConfiguration(configuration)) {
                System.out.println("Configuration saved successfully");
                Logger.logInfo("Configuration saved successfully");
            } else {
                System.out.println("Failed to save configuration.");
            }

            ticketPool = new TicketPool(maxTicketCapacity);
            ticketPool.addTickets(totalTickets);

            // Create and add vendors to the list
            int i;
            for(i = 0; i < numVendors; ++i) {
                Vendor vendor = new Vendor(i + 1, Math.max(1, totalTickets / numVendors), ticketReleaseRate, ticketPool);
                vendors.add(vendor);
            }

            // Create and add customers to the list
            for(i = 0; i < numCustomers; ++i) {
                Customer customer = new Customer("Customer-" + (i + 1), customerRetrievalRate, ticketPool);
                customers.add(customer);
            }

            System.out.println("Please tap the Enter button to start the ticketing system...");

            // Wait for the user to press Enter
            scanner.nextLine(); // This waits for the user to press Enter

            // Start the simulation
            startSimulation();

            // Allow the simulation to run for 10 seconds
            Thread.sleep(10000L);

            // Stop the simulation
            stopSimulation();

            System.out.println("Simulation Completed!");
            System.out.println("Remaining tickets in pool: " + ticketPool.getAvailableTickets());
            Logger.logInfo("Simulation Completed. Remaining tickets: " + ticketPool.getAvailableTickets());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }

    // Method to validate user input
    public static int validate(Scanner scanner) {
        while(true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                }

                System.out.println("Please enter a positive value greater than 0.");
            } catch (NumberFormatException var3) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method to start the simulation
    private static void startSimulation() {
        running = true;
        Logger.logInfo("Simulation Started");
        System.out.println("Simulation Started");

        // Iterate through the list of vendors
        Iterator iterate = vendors.iterator();

        Thread customerThread;
        while(iterate.hasNext()) {
            Vendor vendor = (Vendor)iterate.next(); // Get next vendor
            customerThread = new Thread(vendor); // Create a new thread
            customerThread.start(); // Start the thread
            vendorThreads.add(customerThread); // Add thread to the list
        }

        // Iterate through the list of customers
        iterate = customers.iterator();

        while(iterate.hasNext()) {
            Customer customer = (Customer)iterate.next(); // Get next customer
            customerThread = new Thread(customer); // // Create a new thread
            customerThread.start(); // Start the thread
            customerThreads.add(customerThread); // Add thread to the list
        }

    }

    private static void stopSimulation() {
        running = false;
        Logger.logInfo("Stopping Simulation");
        System.out.println("Stopping Simulation");

        // Iterate through the list of vendors
        Iterator iterate = vendors.iterator();

        while(iterate.hasNext()) {
            Vendor vendor = (Vendor)iterate.next(); // Calling the next vendor
            vendor.stop(); // Call the stop method on vendor
        }

        // Iterate through the list of customers
        // Calling each customer
        // Call the stop method on customer
        iterate = customers.iterator();

        while(iterate.hasNext()) {
            Customer customer = (Customer)iterate.next();
            customer.stop();
        }

        // Iterate through the list of vendor threads
        iterate = vendorThreads.iterator();

        Thread thread;
        while(iterate.hasNext()) {
            thread = (Thread)iterate.next();

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Iterate through the list of customer threads
        iterate = customerThreads.iterator();

        while(iterate.hasNext()) {
            thread = (Thread)iterate.next();

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Logger.logInfo("Simulation Stopped");
        System.out.println("Simulation Stopped");
    }
}