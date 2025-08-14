# Project Title: Custodian

Full-Stack, Cloud-Native Architecture – Designed and deployed a production-grade CRUD application with secure JWT-based authentication, PostgreSQL (managed via Supabase), and AWS S3 object storage for high-fidelity profile image management.

Enterprise-Grade Data Handling – Implements ACID-compliant relational persistence, encrypted data transport (TLS 1.3), and geo-redundant asset storage, ensuring durability, scalability, and low-latency access.

Scalable Deployment Pipeline – Backend service orchestrated and auto-scaled via Render’s cloud infrastructure with zero-downtime CI/CD deployments, optimized query execution, and high availability.

## Table of Contents

- [About the Project](#about-the-project)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Local Development Setup](#local-development-setup)
- [Running with Docker (Local)](#running-with-docker-local)
- [Deployment](#deployment)
- [API Endpoints](#api-endpoints)
- [Screenshots/Demo](#screenshotsdemo)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

---

## About the Project

Custodian is a robust backend API for a customer management system. It's designed to securely handle customer profiles, authenticate users, and manage profile images. This project provides a secure, scalable, and efficient foundation for a modern web application.

**Problem Solved:** This application addresses the need for a centralized, secure system to manage customer information, including personal details and associated profile pictures. It simplifies the process of storing, retrieving, and updating customer data.

**Motivation:** The motivation behind building Custodian was to demonstrate proficiency in a modern Java ecosystem (Spring Boot 3.x) combined with cloud-native practices (Docker, AWS S3) and robust database management (PostgreSQL). It serves as a practical example of building a full-featured backend for a common business requirement.

**Primary Functionalities:**
* **Customer Lifecycle Management:** Comprehensive CRUD operations for customer data.
* **User Authentication:** Secure user registration and login.
* **Profile Picture Management:** Capabilities to upload, store, and retrieve customer profile images efficiently.

### Key Features

* **User Authentication & Authorization:** Secure user login and role-based access control using JWT.
* **Customer Profile Management:** CRUD (Create, Read, Update, Delete) operations for customer data.
* **Profile Image Storage:** Securely upload, store, and retrieve customer profile images using AWS S3.
* **Database Management:** Efficient and reliable data storage with PostgreSQL.
* **Scalable Architecture:** Designed for high availability and performance in a cloud environment.
* **Containerized Deployment:** Packaged as a Docker image for consistent deployment across environments.

---

## Tech Stack

This project leverages a modern and robust tech stack for building a high-performance, scalable backend.

**Backend:**
* **Java 17:** The core programming language.
* **Spring Boot 3.x:** Framework for rapid application development, providing RESTful API capabilities.
    * **Spring Web:** For building RESTful web services.
    * **Spring Data JPA:** For database interactions and ORM.
    * **Spring Security:** For authentication and authorization.
    * **JWT (JSON Web Tokens):** For stateless authentication.
* **Lombok:** Reduces boilerplate code.
* **HikariCP:** High-performance JDBC connection pool.

**Database:**
* **PostgreSQL:** Relational database for persistent data storage.
    * **Render PostgreSQL:** Managed PostgreSQL service used for production-grade database hosting.
    * **Flyway:** Database migration tool for version controlling schema changes.

**Storage:**
* **AWS S3 (Simple Storage Service):** For scalable and durable storage of profile images.

**Containerization:**
* **Docker:** For packaging the application into a portable container.

**Deployment:**
* **Render.com:** Cloud platform for deploying and scaling the Dockerized backend application.
* **Netlify:** For hosting the frontend application.

**Other Tools:**
* **Maven:** Dependency management and build automation.
* **Git:** Version control.

---

## Architecture

This diagram illustrates the flow of data from client requests to the backend API, interacting with PostgreSQL for data persistence and AWS S3 for object storage, all deployed within a Docker container on Render.com.

[Architecture Diagram](https://ibb.co/mrF445VF)

**Components & Flow:**
* **Client (Web/Mobile):** Makes API calls to the backend, hosted on Netlify.
* **Spring Boot Backend (Docker Container):** Deployed on Render.com.
    * Handles incoming REST requests.
    * Authenticates users via JWT.
    * Interacts with **Render PostgreSQL** for customer data.
    * Interacts with **AWS S3** for storing and retrieving profile images.
* **Render PostgreSQL:** Managed PostgreSQL database for the backend.
* **AWS S3:** Object storage for customer profile images.

---

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed:

* **Java 17 JDK:** [Download Link](https://www.oracle.com/java/technologies/downloads/) (or use SDKMAN!, Homebrew)
* **Maven 3.x:** [Download Link](https://maven.apache.org/download.cgi)
* **Docker Desktop:** [Download Link](https://www.docker.com/products/docker-desktop/) (Required for running the app in a Docker container locally)
* **Git:** [Download Link](https://git-scm.com/downloads)
* **PostgreSQL Client:** (Optional, for direct database access)
* **AWS CLI:** (If you need to configure local AWS credentials for S3 access during local testing)

### Local Development Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/vijaysm-git/springboot-api-custodian.git](https://github.com/vijaysm-git/springboot-api-custodian.git)
    cd springboot-api-custodian
    ```

2.  **Configure Database:**
    * **Local PostgreSQL (recommended for local dev):**
        * Install PostgreSQL locally.
        * Create a new database (e.g., `custodian_db`).
        * Create a new user and password for this database.
    * **Or use your Render PostgreSQL (for advanced local testing):**
        * Ensure your local machine's IP is whitelisted in your Render PostgreSQL settings (if applicable, Render often handles this differently than AWS RDS).
        * Obtain your Render PostgreSQL internal/external connection string, username, and password.

3.  **Configure `application.yml` (or `application.properties`):**
    * Navigate to `src/main/resources/`.
    * Rename `application-example.yml` to `application.yml` (or create a new `application.yml`).
    * Update the database connection details and AWS S3 credentials.

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://[YOUR_DB_HOST]:5432/[YOUR_DB_NAME]
        username: [YOUR_DB_USERNAME]
        password: [YOUR_DB_PASSWORD]
        driver-class-name: org.postgresql.Driver
      jpa:
        hibernate:
          ddl-auto: update # or 'none' if using Flyway and prefer explicit migrations
        show-sql: true
        properties:
          hibernate:
            format_sql: true
            dialect: org.hibernate.dialect.PostgreSQLDialect

    aws:
      s3:
        bucket-name: [YOUR_S3_BUCKET_NAME]
        region: [YOUR_AWS_REGION] # e.g., us-east-1

    # Spring Security & JWT Configuration
    application:
      security:
        jwt:
          secret-key: [YOUR_SECURE_JWT_SECRET_KEY] # Generate a long, random string
          expiration: 86400000 # 24 hours in milliseconds
          refresh-token:
            expiration: 604800000 # 7 days in milliseconds
    ```
    * For AWS S3 credentials, ensure your AWS CLI is configured with credentials (`aws configure`) or you provide `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables.

4.  **Run Flyway Migrations (if applicable):**
    ```bash
    mvn flyway:migrate
    ```

5.  **Build the project:**
    ```bash
    mvn clean install
    ```

6.  **Run the application:**
    ```bash
    java -jar target/custodian-0.0.1-SNAPSHOT.jar # Replace with your actual JAR name if different
    ```
    The application will typically start on `http://localhost:8080`.

### Running with Docker (Local)

1.  **Build the Docker image:**
    ```bash
    docker build -t custodian-app:latest .
    ```
    *Make sure you've updated the `ARG JAR_FILE` in your `Dockerfile` to match your actual JAR name.*

2.  **Run the Docker container:**
    ```bash
    docker run -d -p 8080:8080 \
      -e SPRING_DATASOURCE_URL="jdbc:postgresql://[YOUR_DB_HOST]:5432/[YOUR_DB_NAME]" \
      -e SPRING_DATASOURCE_USERNAME="[YOUR_DB_USERNAME]" \
      -e SPRING_DATASOURCE_PASSWORD="[YOUR_DB_PASSWORD]" \
      -e AWS_S3_BUCKET_NAME="[YOUR_S3_BUCKET_NAME]" \
      -e AWS_REGION="[YOUR_AWS_REGION]" \
      -e APPLICATION_SECURITY_JWT_SECRET_KEY="[YOUR_SECURE_JWT_SECRET_KEY]" \
      -e APPLICATION_SECURITY_JWT_EXPIRATION="86400000" \
      -e APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION="604800000" \
      custodian-app:latest
    ```
    *Remember to replace `[PLACEHOLDERS]` with your actual values. Note the Spring Boot environment variable naming convention for Docker (`SPRING_DATASOURCE_URL`, `APPLICATION_SECURITY_JWT_SECRET_KEY`).*
    *For AWS credentials in Docker for local testing, you typically rely on `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables if not using IAM roles.*

---

## Deployment

This section describes how the Dockerized application is deployed to a cloud environment.

### Backend Deployment: Render.com

1.  **Build Docker Image Locally:**
    ```bash
    docker build -t custodian-app:latest .
    ```
2.  **Push Docker Image to a Registry (e.g., Docker Hub or GitHub Container Registry):**
    * Log in to your chosen registry: `docker login`
    * Tag your local image: `docker tag custodian-app:latest your_docker_username/custodian-app:latest`
    * Push the image: `docker push your_docker_username/custodian-app:latest`
3.  **Deploy on Render.com:**
    * Go to your Render Dashboard and create a new "Web Service".
    * Connect your Git repository or choose "Public Docker Image" and paste your image URL (e.g., `your_docker_username/custodian-app:latest`).
    * Select "Docker" as the build command.
    * Configure environment variables under the "Environment" section. These should match the `application.yml` structure or the Docker `e` flags, for example:
        * `SPRING_DATASOURCE_URL`: (Your Render PostgreSQL internal connection string)
        * `SPRING_DATASOURCE_USERNAME`: (Your Render PostgreSQL username)
        * `SPRING_DATASOURCE_PASSWORD`: (Your Render PostgreSQL password)
        * `AWS_S3_BUCKET_NAME`: (Your S3 Bucket Name)
        * `AWS_REGION`: (Your AWS Region)
        * `APPLICATION_SECURITY_JWT_SECRET_KEY`: (Your Secure JWT Secret Key)
        * `APPLICATION_SECURITY_JWT_EXPIRATION`: `86400000`
        * `APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION`: `604800000`
    * Ensure your Render service exposes port `8080`.
    * Ensure your Render service's outbound IP addresses are whitelisted in your AWS S3 bucket policy (if required for direct access, or ensure IAM role on Render is configured correctly).

### Frontend Deployment: Netlify

The frontend application (if separate) is deployed on Netlify.

1.  **Connect GitHub Repository:** Link your frontend GitHub repository to Netlify.
2.  **Configure Build Settings:** Set your build command (e.g., `npm run build` or `yarn build`) and publish directory (e.g., `build`, `dist`).
3.  **Environment Variables:** Configure environment variables in Netlify for your frontend to point to the deployed Render backend API URL.

---

## API Endpoints
Document your key API endpoints here. For more detailed documentation, refer to the Postman API documentation link provided below.

Base URL (Local): http://localhost:8080/api/v1
Base URL (Deployed): https://backend-springboot-v1-0.onrender.com/api/v1

Method	Endpoint	Description
POST	/customers	Registers a new customer account (issues JWT upon success).
POST	/auth/login	Authenticates a user and issues a JWT token.
GET	/customers	Retrieves a list of all registered customers.
GET	/customers/{id}	Retrieves a specific customer by ID.
PUT	/customers/{id}	Updates an existing customer's details by ID.
DELETE	/customers/{id}	Deletes an existing customer record by ID.
POST	/customers/{id}/profile-image	Uploads a profile image for a customer.
GET	/customers/{id}/profile-image	Retrieves a customer's profile image
**Postman API Documentation:** [https://documenter.getpostman.com/view/37432471/2sB2qah1fB](https://documenter.getpostman.com/view/37432471/2sB2qah1fB)

---

## Roadmap

* **Authentication:** Implement OAuth2/OpenID Connect for enhanced security and flexibility.
* **Error Handling & Logging:** More robust error handling mechanisms and comprehensive logging for better debugging and monitoring.
* **Testing:** Add comprehensive unit and integration tests to ensure code quality and stability.
* **Pagination & Filtering:** Implement pagination and advanced filtering for customer listings to improve performance with large datasets.
* **Caching:** Integrate a caching mechanism (e.g., Redis) to reduce database load and improve response times.
* **Frontend Enhancements:** Further develop the dedicated frontend application to include more features and a richer user experience.

---

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---

## License

Distributed under the MIT License. See `LICENSE` for more information.

---

## Contact

Vijay SM - reachvijaysm@outlook.com

Project Link: [https://github.com/vijaysm-git/springboot-api-custodian](https://github.com/vijaysm-git/springboot-api-custodian)
Live Project (Frontend): [https://custodian-springboot.netlify.app/](https://custodian-springboot.netlify.app/)
Live Project (Backend API - Render): [https://backend-springboot-v1-0.onrender.com] (e.g., `https://backend-springboot-v1-0.onrender.com`)

---

## Acknowledgements

* [Spring Boot Documentation](https://spring.io/guides/gs/rest-service/)
* [PostgreSQL Documentation](https://www.postgresql.org/docs/)
* [AWS S3 Documentation](https://aws.amazon.com/s3/)
* [Docker Documentation](https://docs.docker.com/)
* [Render Documentation](https://render.com/docs/)
* [Netlify Documentation](https://docs.netlify.com/)
* [Choose an Open Source License](https://choosealicense.com/)
