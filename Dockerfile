FROM openjdk:8

EXPOSE 8080
ADD target/taskOrganizer.jar taskOrganizer.jar
ENTRYPOINT ["java","-jar","/taskOrganizer.jar"]

ARG APP_NAME=taskOrganizer
ARG APP_VERSION=0.0.1