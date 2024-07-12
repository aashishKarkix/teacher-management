# Teacher Group Routine Management API

This project implements a RESTful API for managing teachers, study groups, and routines in a educational management system.

## Technology Stack

The application is built using the following technologies:

- **Java**: Programming language used for backend development.
- **Spring Boot**: Framework for creating robust Java applications.
- **Spring Data JPA**: Provides easy integration with JPA repositories.
- **Spring Web**: Implements web application features using Spring.
- **PostgreSQL**: Database management system used for data storage.
- **JUnit**: Framework for unit testing.
- **Lombok**: Library to reduce boilerplate code.

## Endpoints

### Routine Controller

#### Create Routine

- **URL:** `/api/routine`
- **Method:** `POST`
- **Description:** Create a new routine.
- **Request Body:** `RoutineDTO`
- **Response:** `RoutineDTO` of the created routine.
- **Status Codes:**
    - `201` if created successfully
    - `400` if routine resource was not found or invalid request
    - `500` if there was an internal server error

#### Get All Routines

- **URL:** `/api/routines`
- **Method:** `GET`
- **Description:** Retrieve all routines.
- **Response:** List of `RoutineDTO`
- **Status Codes:**
    - `200` if successful
    - `500` if there was an internal server error

#### Get Routine by ID

- **URL:** `/api/routine/{id}`
- **Method:** `GET`
- **Description:** Retrieve a routine by its ID.
- **Path Variable:** `id` (Long)
- **Response:** `RoutineDTO` of the fetched routine.
- **Status Codes:**
    - `200` if found
    - `404` if routine not found
    - `500` if there was an internal server error

#### Update Routine

- **URL:** `/api/routine/{id}`
- **Method:** `PUT`
- **Description:** Update a routine by its ID.
- **Path Variable:** `id` (Long)
- **Request Body:** Updated `RoutineDTO`
- **Response:** `RoutineDTO` of the updated routine.
- **Status Codes:**
    - `200` if updated successfully
    - `400` if routine resource was not found or invalid request
    - `404` if routine not found
    - `500` if there was an internal server error

#### Delete Routine

- **URL:** `/api/routine/{id}`
- **Method:** `DELETE`
- **Description:** Delete a routine by its ID.
- **Path Variable:** `id` (Long)
- **Response:** No content.
- **Status Codes:**
    - `204` if deleted successfully
    - `404` if routine not found
    - `500` if there was an internal server error

#### Get Teacher Workload

- **URL:** `/api/teacher/workload`
- **Method:** `GET`
- **Description:** Calculate workload for a teacher within a specified date range.
- **Request Parameters:**
    - `teacherName` (String): Name of the teacher
    - `startDate` (String): Start date (YYYY-MM-DD)
    - `endDate` (String): End date (YYYY-MM-DD)
- **Response:** Workload time in hours (long).
- **Status Codes:**
    - `200` if successful
    - `400` if parameters are missing or invalid
    - `500` if there was an internal server error
- - **example:** : http://localhost:8080/api/teacher/workload?teacherName=Aashish&startDate=2024-07-01&endDate=2024-07-31

#### Get Group Workload

- **URL:** `/api/group/workload`
- **Method:** `GET`
- **Description:** Calculate workload for a study group based on group ID.
- **Request Parameters:**
    - `groupId` (Long): ID of the study group
- **Response:** Workload time in hours (long).
- **Status Codes:**
    - `200` if successful
    - `400` if group ID is missing or invalid
    - `500` if there was an internal server error
- **example:** http://localhost:8080/api/group/workload?groupId=1

### Study Group Controller

#### Get Study Group by ID

- **URL:** `/api/groups/{id}`
- **Method:** `GET`
- **Description:** Retrieve a study group by its ID.
- **Path Variable:** `id` (Long)
- **Response:** `StudyGroup` of the fetched study group.
- **Status Codes:**
    - `200` if found
    - `404` if study group not found
    - `500` if there was an internal server error

#### Create Study Group

- **URL:** `/api/groups`
- **Method:** `POST`
- **Description:** Create a new study group.
- **Request Body:** `StudyGroupDTO`
- **Response:** `StudyGroupDTO` of the created study group.
- **Status Codes:**
    - `201` if created successfully
    - `400` if group resource was not found or invalid request
    - `500` if there was an internal server error

#### Update Study Group

- **URL:** `/api/groups/{id}`
- **Method:** `PUT`
- **Description:** Update a study group by its ID.
- **Path Variable:** `id` (Long)
- **Request Body:** Updated `StudyGroupDTO`
- **Response:** `StudyGroupDTO` of the updated study group.
- **Status Codes:**
    - `200` if updated successfully
    - `400` if group resource was not found or invalid request
    - `404` if study group not found
    - `500` if there was an internal server error

#### Delete Study Group

- **URL:** `/api/groups/{id}`
- **Method:** `DELETE`
- **Description:** Delete a study group by its ID.
- **Path Variable:** `id` (Long)
- **Response:** No content.
- **Status Codes:**
    - `204` if deleted successfully
    - `404` if study group not found
    - `500` if there was an internal server error

### Teacher Controller

#### Get Teacher by ID

- **URL:** `/api/teachers/{id}`
- **Method:** `GET`
- **Description:** Retrieve a teacher by its ID.
- **Path Variable:** `id` (Long)
- **Response:** `TeacherDTO` of the fetched teacher.
- **Status Codes:**
    - `200` if found
    - `404` if teacher not found
    - `500` if there was an internal server error

#### Create Teacher

- **URL:** `/api/teachers`
- **Method:** `POST`
- **Description:** Create a new teacher.
- **Request Body:** `TeacherDTO`
- **Response:** `TeacherDTO` of the created teacher.
- **Status Codes:**
    - `201` if created successfully
    - `400` if teacher resource was not found or invalid request
    - `500` if there was an internal server error

#### Update Teacher

- **URL:** `/api/teachers/{id}`
- **Method:** `PUT`
- **Description:** Update a teacher by its ID.
- **Path Variable:** `id` (Long)
- **Request Body:** Updated `TeacherDTO`
- **Response:** `TeacherDTO` of the updated teacher.
- **Status Codes:**
    - `200` if updated successfully
    - `400` if teacher resource was not found or invalid request
    - `404` if teacher not found
    - `500` if there was an internal server error

#### Delete Teacher

- **URL:** `/api/teachers/{id}`
- **Method:** `DELETE`
- **Description:** Delete a teacher by its ID.
- **Path Variable:** `id` (Long)
- **Response:** No content.
- **Status Codes:**
    - `204` if deleted successfully
    - `404` if teacher not found
    - `500` if there was an internal server error

## Error Handling

The API handles various exceptions and errors using custom exception classes (`IdNotFoundException`, `RoutineResourceException`, `TeacherResourceException`, `GroupResourceException`) to provide meaningful error messages and appropriate HTTP status codes.

## Global Exception Handling

To handle exceptions globally across all controllers, a `ControllerAdvice` class is implemented. This centralizes exception handling logic and ensures consistent error responses throughout the application.


## Logging

Logging using SLF4J is integrated throughout the controllers to record important events and error messages.

## Dependencies

This project uses Spring Boot with dependencies managed via Gradle.

## Setup

To run the project locally:

1. Clone the repository.
2. Set up a PostgreSQL database and configure the connection in `application.properties`.
3. Build the project using gradle command: `./gradlew clean build -x test`.
4. Run the application using your IDE or command line.
5. Access the endpoints through `http://localhost:8080/api`.
