# Leave Service

This module manages employee leave requests and approvals.

## Role

*   **Technology:** Spring Boot, Spring Security (JWT), Spring Data MongoDB, Eureka Client.
*   **Purpose:** To manage employee leave data, including submitting and approving leave requests.
*   **Port:** `8083`

### Key Responsibilities:

*   **Leave Management:** Provides REST APIs for creating, reading, updating, and deleting leave requests.
*   **Leave Approval:** Allows managers to approve or deny leave requests.
*   **Authorization:** Secures its endpoints by validating JWTs on incoming requests.
*   **Service Discovery:** Acts as a client to the Discovery Server, allowing it to find and communicate with other services by name.
