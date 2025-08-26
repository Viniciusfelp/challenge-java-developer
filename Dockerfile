# Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]