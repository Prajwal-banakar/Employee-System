# Employee Management System - Microservices

This project demonstrates the evolution of a monolithic application into a distributed system using a microservices architecture. It serves as a practical example for learning about service decomposition, service discovery, centralized security, and routing.

> **For a detailed explanation of the architecture and workflow, please see the [USER_GUIDE.md](USER_GUIDE.md).**

## Modules

This project is composed of four core modules. See the README in each module's directory for more specific details.

*   **[`discovery-server`](./discovery-server/README.md):** A Netflix Eureka server that acts as the service registry.
*   **[`gateway-service`](./gateway-service/README.md):** The single entry point for all requests. It handles routing, security (JWT validation), and other cross-cutting concerns.
*   **[`employee-service`](./employee-service/README.md):** Manages core employee data and exposes the login endpoint.
*   **[`payroll-service`](./payroll-service/README.md):** Manages detailed employee salary and payroll data.

## Core Technologies

*   **Java 21** & **Spring Boot 3**
*   **Spring Cloud Gateway** for API Routing
*   **Spring Cloud** (Netflix Eureka) for Service Discovery
*   **Spring Security** (with JWT) for Authentication
*   **Spring Data MongoDB** for Data Persistence
*   **Maven** for Build Management

## How to Build and Run

### Prerequisites

*   **Java Development Kit (JDK) 21**
*   **Apache Maven**
*   **MongoDB**

### 1. Build the Entire Project

Build all modules by running the following command from the project root directory:

```sh
mvn clean install
```

### 2. Run the Services

You must run the services in the correct order. Open four separate terminals.

**Terminal 1: Start the Discovery Server**
```sh
cd discovery-server
java -jar target/discovery-server-0.0.1-SNAPSHOT.jar
```
Wait for it to start (on port `8761`).

**Terminal 2: Start the Employee Service**
```sh
cd employee-service
java -jar target/employee-service-0.0.1-SNAPSHOT.jar
```
This service will register with Eureka (on port `8082`).

**Terminal 3: Start the Payroll Service**
```sh
cd payroll-service
java -jar target/payroll-service-0.0.1-SNAPSHOT.jar
```
This service will also register with Eureka (on port `8081`).

**Terminal 4: Start the API Gateway**
```sh
cd gateway-service
java -jar target/gateway-service-0.0.1-SNAPSHOT.jar
```
This is the single entry point for the system (on port `8080`).

Once all services are running, the system is fully operational. All API requests should be made to the gateway on port `8080`.
