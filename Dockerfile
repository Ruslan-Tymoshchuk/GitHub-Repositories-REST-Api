FROM openjdk:17-alpine
COPY target/*.jar github-repositories-rest-api-0.0.1.jar
ENTRYPOINT ["java", "-jar", "github-repositories-rest-api-0.0.1.jar"]