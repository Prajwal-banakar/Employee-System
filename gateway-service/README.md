# API Gateway Service

This module is the **single entry point** for all external requests into the Employee Management System.

## Role

*   **Technology:** Spring Cloud Gateway, Eureka Client, JWT.
*   **Purpose:** To provide a unified and secure interface for all the downstream microservices.
*   **Port:** `8080`

### Key Responsibilities:

*   **Routing:** It dynamically routes incoming requests to the appropriate microservice (e.g., requests to `/api/employees/**` are sent to the `employee-service`) using service discovery.
*   **Centralized Security:** It acts as the security checkpoint for the entire system. It validates the JWT on all incoming requests before they are allowed to proceed to other services.
*   **Cross-Cutting Concerns:** It is the ideal place to implement other cross-cutting concerns like rate limiting, request logging, and caching.
