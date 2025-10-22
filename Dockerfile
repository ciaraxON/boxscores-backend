# Use an official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project files
COPY . .

# Build the project (skip tests to speed it up)
RUN ./mvnw clean package -DskipTests

# Run the built JAR in a lightweight runtime container
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the compiled JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java","-jar","app.jar"]
