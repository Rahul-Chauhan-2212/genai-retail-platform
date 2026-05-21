FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY agents/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY target/auth-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]