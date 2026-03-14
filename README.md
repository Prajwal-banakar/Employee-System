# Employee Management System - Microservices

This project is a distributed system that demonstrates key microservices concepts. It has been decomposed from a monolithic application into a set of communicating services.

## Architecture

The system is composed of two main services:

*   **`employee-service`**: Manages core employee data (name, contact info, role) and authentication.
*   **`payroll-service`**: Manages employee salary information.

## Technologies Used

*   **Java 21**
*   **Spring Boot 3**
*   **Spring Security (with JWT)**
*   **Spring Data MongoDB**
*   **Springdoc OpenAPI (Swagger)**
*   **Maven**

## How to Build and Run

### Prerequisites

*   **Java Development Kit (JDK) 21**
*   **Apache Maven**
*   **MongoDB**

### 1. Build the Entire Project

First, build both services by running the following command from the project root directory (`C:/projects/EmployeeManagement`):

```sh
mvn clean install
```

This will compile, test, and package both services into executable JAR files.

### 2. Run the Services

You need to run each service in a separate terminal.

**Terminal 1: Run the `payroll-service`**

```sh
cd payroll-service
java -jar target/payroll-service-0.0.1-SNAPSHOT.jar
```
This service will start on port `8081`.

**Terminal 2: Run the `employee-service`**

```sh
cd employee-service
java -jar target/employee-service-0.0.1-SNAPSHOT.jar
```
This service will start on port `8080`.

## Security with JWT

The API is secured using JSON Web Tokens (JWT). To access protected endpoints, you must first obtain a token.

**1. Get a Token**

Send a `POST` request to the `/auth/login` endpoint with the following credentials:

```json
{
    "username": "user",
    "password": "password"
}
```

The response will contain a JWT.

**2. Use the Token**

Include the JWT in the `Authorization` header for all subsequent requests to protected endpoints:

```
Authorization: Bearer <your_jwt_token>
```

## API Endpoints (`employee-service`)

| Method | Endpoint                      | Description                                      |
|--------|-------------------------------|--------------------------------------------------|
| POST   | `/auth/login`                 | Authenticates and returns a JWT.                 |
| GET    | `/api/employees/`             | Returns a welcome message.                       |
| POST   | `/api/employees/create`       | Creates a new employee.                          |
| PUT    | `/api/employees/update`       | Updates an existing employee's details.          |
| DELETE | `/api/employees/remove/{id}`  | Removes an employee by their ID.                |
| DELETE | `/api/employees/removeall`    | Removes all employees from the system.           |
| GET    | `/api/employees/viewall`      | Returns a paginated list of all employees.       |
| GET    | `/api/employees/{id}/salary`  | Gets an employee's salary from the `payroll-service`. |
