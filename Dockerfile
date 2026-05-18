FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/jwt-resource-server-1.0.0.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
