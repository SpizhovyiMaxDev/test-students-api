# ===========================================
# STAGE 1: BUILD THE APPLICATION
# ===========================================
# Use Maven image with Java 21 to build our Spring Boot app
FROM maven:3.9.6-openjdk-21 AS build

# Set working directory inside the container
# This is where all our files will be placed
WORKDIR /app

# Copy Maven configuration file first (for better caching)
# Docker will cache this layer and only rebuild if pom.xml changes
COPY pom.xml .

# Copy source code into the container
# This includes all our Java files, resources, etc.
COPY src ./src

# Build the application using Maven
# - clean: Remove old build files
# - package: Create the JAR file
# -DskipTests: Skip running tests (faster build)
RUN mvn clean package -DskipTests

# ===========================================
# STAGE 2: CREATE PRODUCTION IMAGE
# ===========================================
# Use smaller JRE-only image (no Maven needed in production)
FROM openjdk:21-jre-slim

# Install curl for health checks (needed to check if app is running)
# Update package list, install curl, then clean up to keep image small
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Set working directory for the final image
WORKDIR /app

# Copy the built JAR file from the build stage
# The JAR file contains our entire Spring Boot application
COPY --from=build /app/target/*.jar app.jar

# Tell Docker that this container will listen on port 8080
# (Render will override this with their own port)
EXPOSE 8080

# Health check: Is our application running?
# - Check every 30 seconds
# - Wait 5 seconds before first check
# - Retry 3 times if it fails
# - Use curl to check the /actuator/health endpoint
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Start the application
# - Use environment variable PORT if set, otherwise use 8080
# - This allows Render to assign any port they want
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]


