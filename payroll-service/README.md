# Payroll Service

This module is a specialized, internal service responsible for managing sensitive employee payroll data.

## Role

*   **Technology:** Spring Boot, Spring Data MongoDB, Eureka Client.
*   **Purpose:** To act as the single source of truth for all payroll-related information.
*   **Port:** `8081`

### Key Responsibilities:

*   **Payroll Data Management:** Provides REST APIs for creating and retrieving structured payroll records, including salary details.
*   **Internal Service:** This service is not intended to be accessed directly by end-users. It only responds to requests from other trusted internal services (like the `employee-service`).
*   **Service Discovery:** Registers itself with the Discovery Server so that other services can find it on the network.
