# Stage 1: Build the JAR (no longer "builder" because we're not building inside)
# No need for a JDK image in the first stage if you build locally.
# This entire first stage would be removed.

# If you choose this option, your Dockerfile would look much simpler:

FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built JAR directly into the image
# IMPORTANT: Replace 'your-app-name-version.jar' with the actual name of your JAR file
# (e.g., 'yourproject-0.0.1-SNAPSHOT.jar' or 'app.jar' if you renamed it)
# Make sure app.jar actually exists in D:\Projects\full-stack-professional\backend
COPY target/*.jar app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Best Practice: Run as a non-root user for security
RUN addgroup --system appuser && adduser -S -s /bin/false -G appuser appuser
USER appuser