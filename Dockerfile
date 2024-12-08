# Use a lightweight JDK image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/multinotes.jar /app/multinotes.jar

# Expose the port your app runs on (default 8080 for Spring Boot)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "multinotes.jar"]
