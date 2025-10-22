# Use an official lightweight Java image
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Give execute permission to mvnw
RUN chmod +x ./mvnw

# Build the project (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Second stage - run the app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
