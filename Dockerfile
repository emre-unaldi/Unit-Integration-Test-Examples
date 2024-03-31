FROM openjdk:20
WORKDIR /app
ADD target/user-service-api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "user-service-api-0.0.1-SNAPSHOT.jar"]