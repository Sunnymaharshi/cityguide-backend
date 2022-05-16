FROM maven:3.8-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY . .
#RUN mvn clean test
#RUN mvn jacoco:report
RUN mvn package  -DskipTests 
##RUN mvn package
#RUN mvn sonar:sonar -Dsonar.qualitygate.wait=true -Dsonar.host.url=https://sonarqube.hashedin.com  -Dsonar.login=0c7d7a39afee7c34f4b6794e9b2b79d0487c2cd9
FROM openjdk:11
EXPOSE 8080
COPY --from=builder /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
