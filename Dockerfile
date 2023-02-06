#FROM openjdk:11-jdk-alpine
FROM adoptopenjdk/openjdk11:latest

ARG JAR_FILE=build/libs/employee-api.jar

WORKDIR /app

COPY ${JAR_FILE} empployee.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","empployee.jar"]