# Use image base with Java installed
FROM openjdk:21-jdk

# Copy the compiled JAR into the image
COPY target/student-api.jar student-api.jar

# Optional: declare working directory
WORKDIR /

# App will listen on port 8080
EXPOSE 8080

# Start the app when a container runs
ENTRYPOINT ["java", "-jar", "/student-api.jar"]

