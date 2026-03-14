# Employee Management System - User Guide

This guide provides a detailed explanation of the project's architecture, the role of each module, and how they all work together.

### High-Level Overview

This project is a **distributed system built on a microservices architecture**. It has been intentionally evolved from a single, monolithic application into a collection of smaller, independent services that collaborate to perform the functions of a complete Employee Management System.

Think of it like a company's internal departments:
*   You have an **HR department** that manages employee records (`employee-service`).
*   You have a separate, secure **Payroll department** that manages salaries (`payroll-service`).
*   And you have a central **company phone book** so the departments know how to contact each other (`discovery-server`).

This separation is the core of the project. It demonstrates how to build applications that are more scalable, resilient, and easier to manage than a single, large application.

---

### Core Architectural Concepts

Two fundamental patterns are at play here:

1.  **Microservices:** Instead of one big application, we have multiple small services, each with a single, well-defined responsibility. This means we can update, deploy, or scale the `payroll-service` without having to touch the `employee-service`.
2.  **Service Discovery:** The services don't have hardcoded knowledge of each other's locations (like `localhost:8081`). Instead, they use a central registry (the **Discovery Server**) to find each other by name. This is crucial for flexibility and resilience in a distributed environment.

---

### Module-by-Module Breakdown

The project is composed of three distinct modules that run as separate applications.

#### 1. `discovery-server` (The Phone Book)
*   **Purpose:** To act as a dynamic service registry for the entire system.
*   **Technology:** **Netflix Eureka**.
*   **How it Works:**
    *   It runs on its own (on port `8761`) and does nothing but maintain a list of all other services that have registered with it.
    *   When other services (like `employee-service`) start, they "call" the Eureka server and register their name (e.g., "payroll-service") and location (e.g., IP address and port).
    *   Services periodically send a "heartbeat" to Eureka to signal that they are still alive. If a service stops sending heartbeats, Eureka removes it from the registry, preventing others from trying to contact a dead service.

#### 2. `employee-service` (The HR Department & Front Door)
*   **Purpose:** To manage the core, non-sensitive employee data and to act as the primary entry point for user interactions, including security.
*   **Technology:** Spring Web, Spring Data MongoDB, **Spring Security with JWT**, **Eureka Client**.
*   **Key Responsibilities:**
    *   **CRUD Operations:** Provides API endpoints for creating, reading, updating, and deleting employee records (name, email, role, etc.).
    *   **Authentication:** Exposes the `/auth/login` endpoint. It validates user credentials and, if successful, generates a JSON Web Token (JWT).
    *   **Authorization:** It uses a `JwtRequestFilter` to inspect every incoming request for a valid JWT, securing all other endpoints.
    *   **Service Discovery:** It is a **client** of the Eureka server. It uses the `@LoadBalanced RestTemplate` to ask Eureka for the location of the `payroll-service` when it needs to fetch salary data.

#### 3. `payroll-service` (The Secure Payroll Department)
*   **Purpose:** To manage sensitive and structured employee payroll data.
*   **Technology:** Spring Web, Spring Data MongoDB, **Eureka Client**.
*   **Key Responsibilities:**
    *   **Data Ownership:** It is the *single source of truth* for all payroll-related information (basic salary, allowances, deductions, etc.).
    *   **Internal API:** It exposes a simple API for other services to query payroll data. It is not intended to be accessed directly by the end-user.
    *   **Service Registration:** Like the `employee-service`, it registers itself with the Eureka server so that other services can find it.

---

### How It All Works Together: A Workflow Example

Let's trace a request to get an employee's salary:

1.  **Login:** The user first sends their credentials (`user`/`password`) to the `employee-service`'s `/auth/login` endpoint. The service validates them and returns a JWT.

2.  **API Request:** The user now makes a `GET` request to `http://localhost:8080/api/employees/{id}/salary`. They include the JWT in the `Authorization: Bearer <token>` header.

3.  **Security Check:** The `JwtRequestFilter` in the `employee-service` intercepts this request. It validates the JWT. If the token is valid, it allows the request to proceed to the controller.

4.  **Service Discovery in Action:**
    *   The `EmployeeController` receives the request and calls the `RestTemplate`.
    *   The `RestTemplate` is configured with `@LoadBalanced` and sees a URL like `http://payroll-service/api/payroll/{id}`. It knows `payroll-service` is a service name, not a real hostname.
    *   It asks the **Eureka Server**: "Where can I find an instance of `payroll-service`?"
    *   Eureka checks its registry and replies, "You can find it at `192.168.1.5:8081`" (or whatever its current location is).

5.  **Inter-Service Communication:** The `RestTemplate` now makes a standard HTTP request to the address it received from Eureka (`http://192.168.1.5:8081/api/payroll/{id}`).

6.  **Response:** The `payroll-service` receives the request, fetches the data from its database, and returns it to the `employee-service`.

7.  **Final Reply:** The `employee-service` receives the data from the `payroll-service` and forwards it back to the user who made the original request.

This entire process demonstrates a decoupled, resilient, and scalable system, which is the primary goal of this project's architecture.
