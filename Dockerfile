

# Use the official Maven image as a base image
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application using Maven
RUN mvn -B package --file pom.xml  

# Create a new image with the JAR file
FROM  openjdk:17-jdk-slim
COPY --from=build /app/target/trademanager-0.0.1-SNAPSHOT.jar /app/trademanager-0.0.1-SNAPSHOT.jar

EXPOSE 6044
# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/trademanager-0.0.1-SNAPSHOT.jar"]
