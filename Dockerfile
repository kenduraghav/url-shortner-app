# Use Eclipse Temurin JDK 21 with Alpine as the base image
FROM eclipse-temurin:21-alpine AS base

# Set application name as a label
LABEL application="url-shortner-app"

# Enable layers for efficient builds
ENV JDK_JAVA_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:TieredStopAtLevel=1"

# Set working directory
WORKDIR /app

# Copy application JAR file to the container
COPY target/*.jar /app/url-shortner-app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/url-shortner-app.jar"]