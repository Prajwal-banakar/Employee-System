# Employee Service

This module is the primary user-facing service and the main entry point for the Employee Management System.

## Role

*   **Technology:** Spring Boot, Spring Security (JWT), Spring Data MongoDB, Eureka Client.
*   **Purpose:** To manage core employee data and handle system security.
*   **Port:** `8080`

### Key Responsibilities:

*   **Employee Management:** Provides REST APIs for creating, reading, updating, and deleting employee records.
*   **Authentication:** Exposes the `/auth/login` endpoint to authenticate users and issue JSON Web Tokens (JWT).
*   **Authorization:** Secures its endpoints by validating JWTs on incoming requests.
*   **Service Discovery:** Acts as a client to the Discovery Server, allowing it to find and communicate with other services (like the `payroll-service`) by name.
