# Project Title: Custodian
A Simple Crud Application that stores the customers data and profile pictures but has a comprehensive tech stack.



## Table of Contents

- [About the Project](#about-the-project)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Local Development Setup](#local-development-setup)
- [Running with Docker](#running-with-docker)
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

Provide a more detailed description of your project here.
* What problem does it solve?
* What motivated you to build it?
* What are its primary functionalities?

**Example:**
This project is a robust backend API for a customer management system, designed to handle customer profiles, authenticate users, and manage profile images. It provides a secure and scalable foundation for a modern web or mobile application.

### Key Features

* **User Authentication & Authorization:** Secure user login and role-based access control using JWT.
* **Customer Profile Management:** CRUD (Create, Read, Update, Delete) operations for customer data.
* **Profile Image Storage:** Securely upload, store, and retrieve customer profile images.
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
    * **AWS RDS:** Managed PostgreSQL service for production-grade database hosting.
    * **Flyway:** Database migration tool for version controlling schema changes.

**Storage:**
* **AWS S3 (Simple Storage Service):** For scalable and durable storage of profile images.

**Containerization:**
* **Docker:** For packaging the application into a portable container.

**Deployment (Specify your current or target platform):**
* **Azure Container Apps:** Serverless container platform for deploying and scaling the application automatically.
* **AWS EC2 Free Tier:** For manual deployment and management of a Docker container on a virtual machine.
* *Alternatively, if using:* **AWS Lightsail** / **Render.com** / **Railway.app** (Mention the specific one)

**Other Tools:**
* **Maven:** Dependency management and build automation.
* **Git:** Version control.

---

## Architecture

Provide a high-level overview of your project's architecture. A diagram here would be extremely beneficial.

![Architecture Diagram](https://placehold.co/800x400/png?text=Your%20Architecture%20Diagram%20Here) *This diagram illustrates the flow of data from client requests to the backend API, interacting with AWS RDS for data persistence and AWS S3 for object storage, all deployed within a Docker container on [Your chosen cloud platform].*

**Example Components & Flow:**
* **Client (Web/Mobile):** Makes API calls to the backend.
* **Spring Boot Backend (Docker Container):**
    * Handles incoming REST requests.
    * Authenticates users via JWT.
    * Interacts with **AWS RDS (PostgreSQL)** for customer data.
    * Interacts with **AWS S3** for storing and retrieving profile images.
* **AWS RDS:** Managed PostgreSQL database.
* **AWS S3:** Object storage for images.

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
* **AWS CLI:** (If you need to configure local AWS credentials for S3/RDS access during local testing, or for deployment)

### Local Development Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/your-repo-name.git](https://github.com/your-username/your-repo-name.git)
    cd your-repo-name
    ```

2.  **Configure Database:**
    * **Local PostgreSQL (recommended for local dev):**
        * Install PostgreSQL locally.
        * Create a new database (e.g., `your_project_db`).
        * Create a new user and password for this database.
    * **Or use your AWS RDS (for advanced local testing):**
        * Ensure your local machine's IP is whitelisted in your RDS Security Group.
        * Obtain your RDS endpoint, username, and password.

3.  **Configure `application.yml` (or `application.properties`):**
    * Navigate to `src/main/resources/`.
    * Rename `application-example.yml` (or create a new `application.yml`).
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
    * For AWS S3 credentials, ensure your AWS CLI is configured with credentials (`aws configure`) or use IAM roles if running on EC2.

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
    java -jar target/your-app-name-0.0.1-SNAPSHOT.jar # Replace with your actual JAR name
    ```
    The application will typically start on `http://localhost:8080`.

### Running with Docker (Local)

1.  **Build the Docker image:**
    ```bash
    docker build -t your-spring-app-image:latest .
    ```
    *Make sure you've updated the `ARG JAR_FILE` in your `Dockerfile` to match your actual JAR name.*

2.  **Run the Docker container:**
    ```bash
    docker run -d -p 8080:8080 \
      -e DB_URL="jdbc:postgresql://[YOUR_DB_HOST]:5432/[YOUR_DB_NAME]" \
      -e DB_USERNAME="[YOUR_DB_USERNAME]" \
      -e DB_PASSWORD="[YOUR_DB_PASSWORD]" \
      -e AWS_S3_BUCKET_NAME="[YOUR_S3_BUCKET_NAME]" \
      -e AWS_REGION="[YOUR_AWS_REGION]" \
      -e SPRING_SECURITY_JWT_SECRET_KEY="[YOUR_SECURE_JWT_SECRET_KEY]" \
      -e SPRING_SECURITY_JWT_EXPIRATION="86400000" \
      -e SPRING_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION="604800000" \
      your-spring-app-image:latest
    ```
    *Remember to replace `[PLACEHOLDERS]` with your actual values.*
    *For AWS credentials in Docker, you typically rely on IAM roles in cloud deployments or provide `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables (less secure for production).*

---

## Deployment

This section describes how to deploy the Dockerized application to a cloud environment.

*Describe your chosen deployment method here. Pick one or two of the below based on what you actually do.*

### Option 1: Azure Container Apps

1.  **Build and Push Docker Image:**
    ```bash
    # Authenticate Docker to Azure Container Registry (if using ACR)
    az acr login --name <your-acr-name>

    # Tag your local image
    docker tag your-spring-app-image:latest <your-acr-name>.azurecr.io/your-spring-app-image:latest

    # Push to ACR
    docker push <your-acr-name>.azurecr.io/your-spring-app-image:latest
    ```
2.  **Deploy to Azure Container Apps:**
    * Create an Azure Container App Environment.
    * Create a new Container App, referencing your image from ACR (or Docker Hub).
    * Configure environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AWS_S3_BUCKET_NAME`, `AWS_REGION`, JWT secrets) in the Container App's "Environment Variables" section.
    * Ensure networking is configured to allow inbound traffic on port 80 (Container Apps handles mapping to 8080 internally).
    * Ensure your Azure Container App's outbound IP addresses are whitelisted in your AWS RDS Security Group.

### Option 2: AWS EC2 Free Tier (Manual Docker Deployment)

1.  **Build and Push Docker Image:** Push your image to **Amazon ECR** or **Docker Hub**. (See [Running with Docker](#running-with-docker) for commands).
2.  **Launch EC2 Instance:**
    * Provision a `t2.micro` (or `t3.micro`) Linux instance in the AWS Console, ensuring it's "Free tier eligible."
    * Configure a Security Group to allow inbound SSH (Port 22) and HTTP (Port 80).
    * Attach an IAM Role to the EC2 instance with permissions to read from AWS S3 and connect to RDS.
3.  **SSH and Install Docker:**
    * SSH into your EC2 instance.
    * Install Docker: `sudo yum install docker -y && sudo systemctl start docker && sudo systemctl enable docker` (for Amazon Linux).
    * Add user to docker group: `sudo usermod -aG docker ec2-user` (then re-login).
4.  **Pull and Run Container:**
    * Login to ECR (if using ECR).
    * `docker pull <your-ecr-repo-url>/your-spring-app-image:latest`
    * `docker run -d -p 80:8080 \
      -e DB_URL="jdbc:postgresql://[YOUR_RDS_ENDPOINT]:5432/[YOUR_DB_NAME]" \
      -e DB_USERNAME="[YOUR_DB_USERNAME]" \
      -e DB_PASSWORD="[YOUR_DB_PASSWORD]" \
      -e AWS_S3_BUCKET_NAME="[YOUR_S3_BUCKET_NAME]" \
      -e AWS_REGION="[YOUR_AWS_REGION]" \
      -e SPRING_SECURITY_JWT_SECRET_KEY="[YOUR_SECURE_JWT_SECRET_KEY]" \
      -e SPRING_SECURITY_JWT_EXPIRATION="86400000" \
      -e SPRING_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION="604800000" \
      your-spring-app-image:latest`
    * Ensure your RDS Security Group allows inbound connections from your EC2 instance's IP.

---

## API Endpoints

Document your key API endpoints here. You can use tools like Swagger/OpenAPI for more detailed documentation, but a brief overview is helpful.

**Base URL:** `http://localhost:8080/api/v1` (or your deployed URL)

| Method | Endpoint                    | Description                                     |
| :----- | :-------------------------- | :---------------------------------------------- |
| `POST` | `/auth/signup`            | Register a new user                             |
| `POST` | `/auth/login`               | Authenticate user and get JWT token             |
| `GET`  | `/customers`                | Retrieve all customers (Admin role required)    |
| `GET`  | `/customers/{id}`           | Retrieve a specific customer by ID              |
| `PUT`  | `/customers/{id}`           | Update a customer's details                     |
| `DELETE`| `/customers/{id}`           | Delete a customer (Admin role required)        |
| `POST` | `/customers/{id}/profile-image`| Upload a profile image for a customer        |
| `GET`  | `/customers/{id}/profile-image`| Retrieve a customer's profile image          |
| `DELETE`| `/customers/{id}/profile-image`| Delete a customer's profile image           |

*(You can expand this with request/response examples or link to a Swagger UI if you have one.)*

---

## Roadmap

* Authentication: Implement OAuth2/OpenID Connect.
* More robust error handling and logging.
* Add comprehensive unit and integration tests.
* Implement pagination and filtering for customer listings.
* Integrate a caching mechanism (e.g., Redis).
* Add a dedicated frontend application.

---

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Push your Changes (`git push origin feature/AmazingFeature`)
4.  Open a Pull Request

---

## License

Distributed under the MIT License. See `LICENSE` for more information.

---

## Contact

Your Name - reachvijaysm@outlook.com
Project Link: 

---

## Acknowledgements

* [Spring Boot Documentation](https://spring.io/guides/gs/rest-service/)
* [PostgreSQL Documentation](https://www.postgresql.org/docs/)
* [AWS S3 Documentation](https://aws.amazon.com/s3/)
* [Docker Documentation](https://docs.docker.com/)
* [Azure Container Apps Documentation](https://learn.microsoft.com/en-us/azure/container-apps/)
* [Choose an Open Source License](https://choosealicense.com/)
