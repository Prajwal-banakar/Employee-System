# Discovery Server

This module is the **service registry** for the entire Employee Management System.

## Role

*   **Technology:** Netflix Eureka Server.
*   **Purpose:** To provide a central "phone book" where all other microservices can register themselves and discover each other.
*   **Port:** `8761`

This server allows the microservices to communicate with each other by their registered names (e.g., `employee-service`) instead of hardcoded network locations, which is essential for a resilient and scalable distributed system.

Once running, the Eureka dashboard can be viewed at `http://localhost:8761`.
