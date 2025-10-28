# ModernBank ATM Reporting Service

## Overview

The ModernBank ATM Reporting Service is a Spring Boot application that
collects, enriches, and distributes operational telemetry for a fleet of ATMs.
It exposes a WebSocket interface for real-time status updates, provides a
pluggable strategy layer for generating PDF invoices from ATM data, and lays
the foundation for downstream integrations such as Kafka-based event streams or
external reporting systems.

The project is designed as a reusable microservice that can sit between ATM
hardware emitters and user-facing dashboards or compliance tooling.

## Key Features

* **Real-time ATM status streaming** – Clients can connect to a STOMP
  WebSocket endpoint (`/atm-websocket`) and subscribe to the topic
  `/topic/atm-status-updates` to receive status notifications published by the
  `ATMWebSocketController`.
* **Structured status payloads** – The domain model includes an `ATM` entity
  with descriptive metadata (location, address, operational flags) and enums for
  ATM lifecycle, deposit, and withdrawal status codes. These structures ensure
  consistent serialization of events and reports.
* **Invoice generation strategies** – The
  `InvoiceGeneratorManager` orchestrates one or more `IInvoiceGenerationStrategy`
  implementations, allowing the service to render downloadable artifacts (for
  example, ATM performance invoices) using libraries such as iText PDF. A sample
  `ATMReportInvoiceGenerator` strategy is provided.
* **Extensible foundation** – The Maven build already includes dependencies for
  Kafka, Redis caching, retry logic, ModelMapper, and encryption (Bouncy Castle)
  so the service can be extended into a fully fledged reporting pipeline.

## Architecture

```
┌──────────────────────────┐
│ ATM Devices & Producers  │
└──────────────┬───────────┘
               │ events / status updates
┌──────────────▼───────────┐        ┌────────────────────────────────┐
│ Spring WebSocket Layer   │        │ Invoice Generation Strategies │
│ • `WebSocketConfiguration`│        │ • `InvoiceGeneratorManager`   │
│ • `ATMWebSocketController`│        │ • `ATMReportInvoiceGenerator` │
└──────────────┬───────────┘        │ • `IInvoiceGenerationStrategy` │
               │ STOMP topic        └────────────────────────────────┘
┌──────────────▼───────────┐
│  UI Dashboard / Clients  │
└──────────────────────────┘
```

### Domain Model

* `ATM` – JPA entity representing an individual machine, including metadata
  such as latitude/longitude, district, and operational timestamps.
* `ATMStatus`, `ATMDepositStatus`, `ATMWithdrawStatus` – Enumerations that map
  normalized status codes to human-friendly descriptions.
* `InvoiceGenerationTypes` – Enumerates the supported invoice renderers so they
  can be discovered dynamically at runtime.

### WebSocket Layer

The WebSocket layer is enabled through `WebSocketConfiguration`, which
configures a simple in-memory broker and exposes a SockJS-enabled STOMP
endpoint at `/atm-websocket`. Incoming messages sent to `/app/atm-status` are
processed by `ATMWebSocketController.handleATMStatusUpdate` and broadcast to all
subscribers on `/topic/atm-status-updates`.

### Invoice Generation

`InvoiceGeneratorManager` receives all available implementations of
`IInvoiceGenerationStrategy` via constructor injection. The manager validates
the requested `generationType` and delegates PDF creation to the matching
strategy. Custom strategies can be registered by implementing
`IInvoiceGenerationStrategy`, annotating the class with `@Component`, and
returning a unique `generationType` key.

## Getting Started

### Prerequisites

* Java 17+
* Maven 3.9+
* (Optional) A running MySQL instance when enabling JPA persistence.

### Installation

```bash
git clone https://github.com/<your-org>/modernbank-atm-reporting-service.git
cd modernbank-atm-reporting-service
mvn clean install
```

### Running the Service

Start the Spring Boot application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The WebSocket endpoint will be available at
`ws://localhost:8080/atm-websocket` (SockJS compatible).

### Connecting to the WebSocket

1. Establish a STOMP connection to `/atm-websocket`.
2. Subscribe to `/topic/atm-status-updates`.
3. Send a message to `/app/atm-status` to broadcast a status update. The
   controller currently echoes the payload, prefixed with `"ATM Status Update:"`.

Example payload:

```json
{
  "atmId": "1234",
  "status": "ACTIVE",
  "timestamp": "2024-06-15T10:31:00Z"
}
```

## Extending the Service

* **Custom status payloads:** Replace the placeholder `ATMStatusUpdateResponse`
  DTO with a full schema and update `ATMWebSocketController` to serialize it.
* **Persistence:** Wire `ATM` into a Spring Data repository and configure
  `application.properties` with MySQL credentials to store historical events.
* **Invoice rendering:** Implement PDF creation inside
  `ATMReportInvoiceGenerator.generatePDF` using iText or integrate alternative
  generators for CSV/Excel outputs.
* **Error handling:** Populate `ResponseStatus` and `ErrorCodeConstants` with
  standard codes so API responses can communicate precise failure modes.

## Testing

Run the standard Spring Boot test suite:

```bash
./mvnw test
```

At present the project includes a basic context-loads test and is intended to
be expanded with WebSocket, repository, and invoice generator tests.

## Project Structure

```
src/
├── main
│   ├── java/com/modernbank/atm_reporting_service
│   │   ├── config/                # WebSocket broker configuration
│   │   ├── constants/             # Placeholder for response & error codes
│   │   ├── model/                 # Entities and enums
│   │   └── websocket/             # Controllers and generation strategies
│   └── resources/application.properties
└── test/java/...                  # Spring Boot context test
```
