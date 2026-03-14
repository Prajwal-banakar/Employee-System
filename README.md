# Employee Management System - Microservices

This project demonstrates the evolution of a monolithic application into a distributed system using a microservices architecture. It serves as a practical example for learning about service decomposition, service discovery, and modern authentication patterns.

## Architecture

The system is composed of three core modules:

*   **`discovery-server`**: A Netflix Eureka server that acts as a dynamic service registry. All other services register here, allowing them to find each other by name instead of using hardcoded URLs.

*   **`employee-service`**: Manages core employee data (name, contact info, role) and handles user authentication with JWT.

*   **`payroll-service`**: Manages detailed employee salary and payroll data.

This decoupled architecture makes the system more resilient, scalable, and easier to manage.

## Technologies Used

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
