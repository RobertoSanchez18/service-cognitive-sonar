FROM openjdk:17-alpine
LABEL authors="Cuadros García"
WORKDIR /app
COPY target/AS221S5_T03_be-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]