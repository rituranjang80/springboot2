FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /data
COPY --from=build /workspace/target/employee-api-0.0.1-SNAPSHOT.jar app.jar
ENV DB_PATH=/data/employees.db
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
