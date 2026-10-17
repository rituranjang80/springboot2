# Spring Boot + SQLite + Swagger + Docker + Jenkins

## Requirements
- JDK 17
- Maven 3.6.3+ (or use Docker for the container build)
- Docker Desktop for Windows, with Docker Compose
- Eclipse IDE (import as an Existing Maven Project)

## Run locally with one command (PowerShell or CMD)
From this project folder:
```powershell
mvn spring-boot:run
```
The app starts at http://localhost:8080. Maven downloads dependencies on first run.

## Run with Docker Compose (one command)
```powershell
docker compose up --build -d
```
Open:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Health check: http://localhost:8080/api/health

Stop:
```powershell
docker compose down
```
The named Docker volume `employee_data` keeps SQLite data between container restarts. `docker compose down -v` deletes that volume and its database.

## API examples
Create employee:
```powershell
curl.exe -X POST http://localhost:8080/api/employees -H "Content-Type: application/json" -d "{\"name\":\"Asha Sharma\",\"email\":\"asha@example.com\",\"department\":\"QA\"}"
```
List:
```powershell
curl.exe http://localhost:8080/api/employees
```
Get one:
```powershell
curl.exe http://localhost:8080/api/employees/1
```
Update:
```powershell
curl.exe -X PUT http://localhost:8080/api/employees/1 -H "Content-Type: application/json" -d "{\"name\":\"Asha Sharma\",\"email\":\"asha@example.com\",\"department\":\"Engineering\"}"
```
Delete:
```powershell
curl.exe -X DELETE http://localhost:8080/api/employees/1
```

## Eclipse
1. Extract the ZIP.
2. Eclipse: File > Import > Maven > Existing Maven Projects.
3. Select the extracted project folder and click Finish.
4. Confirm Eclipse uses JDK 17: Window > Preferences > Java > Installed JREs.
5. Right-click `EmployeeApiApplication.java` > Run As > Java Application.
6. If dependency errors appear: right-click project > Maven > Update Project.

## Jenkins
The included `Jenkinsfile` expects Jenkins tools named `JDK17` and `Maven3`, plus a Docker CLI/daemon available to the agent. Configure these under Manage Jenkins > Tools and ensure the agent has permission to run Docker. Create a Pipeline job pointing at this repository. The pipeline runs `mvn clean verify` and builds a Docker image tagged with the Jenkins build number.

This Jenkinsfile builds the image; it does not automatically push to a registry or replace a running deployment. Add registry credentials and an approved deployment target before enabling automated release.

## Tomcat note
The default Docker image runs Spring Boot's embedded Tomcat on port 8080. This is already Apache Tomcat inside the Spring Boot runtime; a separate Tomcat installation is not needed. Deploying to a standalone Tomcat server requires changing the Maven packaging to WAR and configuring the servlet initializer.
