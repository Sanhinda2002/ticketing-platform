package com.ticketingplatform.ticketing_platform.Models;

import jakarta.persistence.*;

// Entity class representing the configuration for an event
@Entity
@Table(name = "eventconfigurations")
public class Configuration {

    // ID of the configuration, auto-generated as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Seeing up columns for each attribute
    @Column(name = "total_tickets", nullable = false)
    private Integer totalTickets;

    @Column(name = "ticket_release_rate")
    private Integer ticketReleaseRate;

    @Column(name = "customer_retrieval_rate")
    private Integer customerRetrievalRate;

    @Column(name = "max_ticket_capacity")
    private Integer maxTicketCapacity;

    @Column(nullable = false)
    private String eventName;

    // Default constructor
    public Configuration(){

    }

    // Constructor to initialize configuration
    public Configuration(Integer totalTickets, Integer ticketReleaseRate, Integer customerRetrievalRate, Integer maxTicketCapacity, String eventName, Long id){
        this.totalTickets=totalTickets;
        this.ticketReleaseRate=ticketReleaseRate;
        this.customerRetrievalRate=customerRetrievalRate;
        this.maxTicketCapacity=maxTicketCapacity;
        this.eventName = eventName;
        this.id =id;
    }

    // Add getter and setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets){
        this.totalTickets=totalTickets;
    }

    public Integer getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(Integer ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public Integer getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(Integer customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public Integer getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(Integer maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public boolean validate() {
        return totalTickets > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0 && maxTicketCapacity > 0;
    }
}
