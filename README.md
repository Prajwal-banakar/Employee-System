# Employee Management System - Microservices

This project demonstrates the evolution of a monolithic application into a distributed system using a microservices architecture. It serves as a practical example for learning about service decomposition, service discovery, and modern authentication patterns.

> **For a detailed explanation of the architecture and workflow, please see the [USER_GUIDE.md](USER_GUIDE.md).**

## Modules

This project is composed of three core modules. See the README in each module's directory for more specific details.

*   **[`discovery-server`](./discovery-server/README.md):** A Netflix Eureka server that acts as the service registry for the system.
*   **[`employee-service`](./employee-service/README.md):** Manages core employee data and handles user authentication with JWT.
*   **[`payroll-service`](./payroll-service/README.md):** Manages detailed employee salary and payroll data.

## Core Technologies

*   **Java 21** & **Spring Boot 3**
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

You must run the services in the correct order. Open three separate terminals.

**Terminal 1: Start the Discovery Server**
```sh
cd discovery-server
java -jar target/discovery-server-0.0.1-SNAPSHOT.jar
```
Wait for it to start (it will be on port `8761`). You can view the Eureka dashboard at `http://localhost:8761`.

**Terminal 2: Start the Payroll Service**
```sh
cd payroll-service
java -jar target/payroll-service-0.0.1-SNAPSHOT.jar
```
This service will register itself with Eureka.

**Terminal 3: Start the Employee Service**
```sh
cd employee-service
java -jar target/employee-service-0.0.1-SNAPSHOT.jar
```
This service will also register itself with Eureka.

Once all services are running, the system is fully operational. API endpoints can be explored via the Swagger UI on the `employee-service` at `http://localhost:8080/swagger-ui.html`.
