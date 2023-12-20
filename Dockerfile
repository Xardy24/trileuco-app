FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY target/trileuco-app-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
