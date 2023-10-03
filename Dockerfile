
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY target/trademanager-0.0.1-SNAPSHOT.jar /app

EXPOSE 9200

CMD ["java", "-jar", "trademanager-0.0.1-SNAPSHOT.jar.jar"]
