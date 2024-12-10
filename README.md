
# Ticketing Platform

This is a comprehensive ticketing platform application built using Spring Boot, designed to manage ticket sales and distribution for events.

## Features

- Event Configuration Management
- Vendor and Customer Interactions
- Logging and Monitoring



## API Reference

#### Load Configurations

```http
  GET /api/load
```
Retrieve all event configurations

#### Save Configuration

```http
  POST /api/save
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Long` | Unique identifier for the configuration |
| `totalTickets` | `Integer` |Total number of tickets for the event |
| `ticketReleaseRate` | `Integer` |Rate at which tickets are released |
| `customerRetrievalRate` | `Integer` |Rate at which customers can retrieve tickets |
| `maxTicketCapacity` | `Integer` | Maximum ticket capacity for the event |
| `eventName` | `string` | Name of the event |

Save a new event configuration

#### Get Events

```http
  GET /api/events
```
Retrieve all events

####  Retrieve Ticket
```http
  POST /api/retrieve
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `customerName`      | `string` | Name of the customer retrieving the ticket |
| `eventName`      | `string` | Name of the event for ticket reservation |

Retrieve a ticket for a customer

####  Release Tickets
```http
  POST /api/release
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `vendorId`      | `Integer` | Unique identifier for the vendor |
| `ticketsPerRelease`      | `Integer` | Number of tickets to be released |

Release tickets by a vendor

####  Stop System
```http
  POST /api/stop
```

Stop the ticketing system
