# ========= 1️⃣ BUILD STAGE =========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build project (skip tests for faster image build)
RUN mvn clean package -DskipTests

# ========= 2️⃣ RUNTIME STAGE =========
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for Spring Boot app
EXPOSE 8083

# Environment variable (optional, for MongoDB connection)
# ENV SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/reactive_users_db

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
