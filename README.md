# Gold Medal Metrics - Full-Stack Application

A full-stack web application to browse and analyze Olympic Gold Medal data, built with a Spring Boot back-end and a JavaScript front-end.

## How to Use

This project is designed to be run as a complete application. The primary way to interact with the data is through the web interface provided.

1.  **Prerequisites:**
    - Java 11 or higher
    - Apache Maven

2.  **Run the Application:**
    - Open a terminal in the project's root directory.
    - Start the application using the Maven wrapper:
      ```shell
      ./mvnw spring-boot:run
      ```

3.  **Access the Web Interface:**
    - Once the application has started, open your favorite web browser.
    - Navigate to the following URL:
      [http://localhost:8080](http://localhost:8080)

    You can now use the web page to sort, filter, and view all the Olympic data.

## Technologies Used

- **Java**: Primary language for the backend.
- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For data access and persistence.
- **H2 Database**: In-memory database for data storage.
- **Maven**: Build automation and dependency management.
- **RESTful API**: Backend architectural style.
- **JavaScript**: Language for the frontend.
- **HTML/CSS**: Frontend structure and styling.
- **Full-Stack Application**: Project scope covering both client and server.
- **Olympic Data Analysis**: The application's domain.

## Data Source

The application uses an in-memory H2 database that is populated on startup from the `data.sql` script located in `src/main/resources/`. This script contains all the necessary medal and country information.

---

## For Developers: API Endpoints

The front-end is powered by a REST API. If you wish to interact with the API directly for testing or development purposes, you can use a tool like `cURL` or Postman. The endpoints are described below.

#### 1. Get All Countries

- **URL:** `/countries`
- **Method:** `GET`
- **Example (cURL):** Get countries sorted by medal count in descending order.
  ```shell
  curl --request GET "http://localhost:8080/countries?sort_by=medals&ascending=n"
  ```

#### 2. Get Country Details

- **URL:** `/countries/{countryName}`
- **Method:** `GET`
- **Example (cURL):** Get the details for the "United States" Olympic team.
  ```shell
  curl --request GET "http://localhost:8080/countries/united%20states"
  ```

#### 3. Get Medals for a Country

- **URL:** `/countries/{countryName}/medals`
- **Method:** `GET`
- **Example (cURL):** Get all medals for "China", sorted by year in ascending order.
  ```shell
  curl --request GET "http://localhost:8080/countries/china/medals?sort_by=year&ascending=y"
  ```
