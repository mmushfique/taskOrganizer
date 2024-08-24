FROM openjdk:8

EXPOSE 8080
ADD target/taskOrganizer.war taskOrganizer.war
ENTRYPOINT ["java","-jar","/taskOrganizer.jar"]

ARG APP_NAME=TaskOrganizer
ARG APP_VERSION=0.0.1