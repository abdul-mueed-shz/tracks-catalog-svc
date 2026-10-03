FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /workspace
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre

WORKDIR /app
RUN useradd --system --uid 10001 --create-home appuser
COPY --from=build /workspace/target/*.jar app.jar
RUN chown appuser:appuser app.jar

USER 10001
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
