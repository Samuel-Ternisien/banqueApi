FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/banque-1.0.0.jar app.jar
    
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
