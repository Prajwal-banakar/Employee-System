# Employee Management System API

This is a robust and secure RESTful API for managing employees, built with Spring Boot and Spring Data MongoDB.

## Features

*   **CRUD Operations**: Create, Read, Update, and Delete employees.
*   **API Documentation**: Interactive API documentation with Swagger UI.
*   **Security**: Basic authentication to protect all endpoints.
*   **Validation**: Server-side validation to ensure data integrity.
*   **DTOs**: Decoupled API from the database model using Data Transfer Objects.
*   **Pagination**: Paginated endpoint for retrieving all employees.
*   **Centralized Exception Handling**: Consistent error responses for all exceptions.

## Technologies Used

*   Java
*   Spring Boot
*   Spring Data MongoDB
*   Spring Security
*   Springdoc OpenAPI (Swagger)
*   Maven

## How to Run

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 21 or later
    *   Apache Maven
    *   MongoDB

2.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/EmployeeManagement.git
    ```

3.  **Configure the database:**
    Open `src/main/resources/application.properties` and configure your MongoDB connection details:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/ems
    ```

4.  **Build and run the application:**
    ```bash
    mvn spring-boot:run
    ```

5.  **Access the API:**
    The API will be running at `http://localhost:8080`.

## API Documentation

Once the application is running, you can access the interactive API documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Security

All endpoints are protected with basic authentication.

*   **Username**: `user`
*   **Password**: `password`

## API Endpoints

| Method | Endpoint                | Description                                      |
|--------|-------------------------|--------------------------------------------------|
| GET    | `/api/employees/`       | Returns a welcome message.                       |
| POST   | `/api/employees/create` | Creates a new employee.                          |
| PUT    | `/api/employees/update` | Updates an existing employee's details.          |
| DELETE | `/api/employees/remove/{id}` | Removes an employee by their ID.                |
| DELETE | `/api/employees/removeall` | Removes all employees from the system.           |
| GET    | `/api/employees/viewall`| Returns a paginated list of all employees.       |
