# Employee Management System - User Guide

This guide provides a detailed explanation of the project's architecture, the role of each module, and how they all work together.

### High-Level Overview

This project is a **distributed system built on a microservices architecture**. It has been intentionally evolved from a single, monolithic application into a collection of smaller, independent services that collaborate to perform the functions of a complete Employee Management System.

Think of it like a company's secure corporate network:
*   You have a **main gate** that checks everyone's ID badge (`gateway-service`).
*   Inside, you have an **HR department** that manages employee records (`employee-service`).
*   You also have a separate, secure **Payroll department** (`payroll-service`).
*   And a central **company phone book** so the departments and the main gate know how to find each other (`discovery-server`).

This separation is the core of the project. It demonstrates how to build applications that are more scalable, resilient, and easier to manage.

---

### Core Architectural Concepts

Three fundamental patterns are at play here:

1.  **Microservices:** Instead of one big application, we have multiple small services, each with a single, well-defined responsibility.
2.  **Service Discovery:** Services don't have hardcoded knowledge of each other's locations. They use a central registry (the **Discovery Server**) to find each other by name.
3.  **API Gateway:** A single entry point that handles all incoming requests. It is responsible for routing, security, and other cross-cutting concerns, acting as a reverse proxy and security checkpoint.

---

### Module-by-Module Breakdown

The project is composed of four distinct modules that run as separate applications.

#### 1. `discovery-server` (The Phone Book)
*   **Purpose:** To act as a dynamic service registry for the entire system.
*   **Technology:** **Netflix Eureka**.
*   **How it Works:** It maintains a live list of all other services and their network locations. All other modules are clients of this server.

#### 2. `gateway-service` (The Main Gate)
*   **Purpose:** To be the single, unified entry point for all external requests.
*   **Technology:** **Spring Cloud Gateway**, Eureka Client, JWT.
*   **Key Responsibilities:**
    *   **Routing:** It uses the discovery server to dynamically route requests to the correct service (e.g., a request to `/api/employees/**` is forwarded to the `employee-service`).
    *   **Centralized Security:** It validates the JWT on all incoming requests (except for login) before they are allowed to proceed to any downstream service.

#### 3. `employee-service` (The HR Department)
*   **Purpose:** To manage the core, non-sensitive employee data.
*   **Technology:** Spring Web, Spring Data MongoDB, Eureka Client.
*   **Key Responsibilities:**
    *   **CRUD Operations:** Provides API endpoints for creating, reading, updating, and deleting employee records.
    *   **Authentication:** Exposes the `/auth/login` endpoint to issue JWTs (but does not validate them itself).
    *   **Service Discovery:** It is a client of the Eureka server, which allows it to find the `payroll-service`.

#### 4. `payroll-service` (The Secure Payroll Department)
*   **Purpose:** To manage sensitive and structured employee payroll data.
*   **Technology:** Spring Web, Spring Data MongoDB, Eureka Client.
*   **Key Responsibilities:**
    *   **Data Ownership:** It is the single source of truth for all payroll-related information.
    *   **Internal API:** It exposes an API intended only for internal communication from other trusted services.

---

### How It All Works Together: A Workflow Example

Let's trace a request to get an employee's salary:

1.  **Login:** The user sends their credentials to the **API Gateway** at `http://localhost:8080/auth/login`. The gateway routes this request to the `employee-service`. The `employee-service` validates the credentials and returns a JWT to the user (via the gateway).

2.  **API Request:** The user now makes a `GET` request to the **API Gateway** at `http://localhost:8080/api/employees/{id}/salary`. They include the JWT in the `Authorization: Bearer <token>` header.

3.  **Security Check at the Gateway:** The `gateway-service` intercepts this request. Its `JwtAuthenticationFilter` validates the JWT. If the token is valid, it allows the request to proceed.

4.  **Routing at the Gateway:** The gateway sees that the path `/api/employees/**` is mapped to the `employee-service`. It asks the **Eureka Server** for the location of `employee-service` and forwards the request there.

5.  **Service Discovery in `employee-service`:**
    *   The `EmployeeController` receives the request and calls the `RestTemplate` to get salary data.
    *   The `RestTemplate` sees the URL `http://payroll-service/api/payroll/{id}` and asks the **Eureka Server** for the location of `payroll-service`.
    *   Eureka provides the address, and the `RestTemplate` makes a request to the `payroll-service`.

6.  **Response:** The `payroll-service` returns the payroll data to the `employee-service`.

7.  **Final Reply:** The `employee-service` returns the data to the **API Gateway**, which then forwards the final response back to the user.
