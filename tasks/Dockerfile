# Etapa 1: build com maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: rodar no tomcat
FROM tomcat:11.0.6-jdk21

COPY --from=build /app/target/todolist.war /usr/local/tomcat/webapps/

EXPOSE 8080