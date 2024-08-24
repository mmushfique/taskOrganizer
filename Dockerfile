FROM openjdk:8

EXPOSE 8080
ADD target/bumbleBee.war bumbleBee.war
ENTRYPOINT ["java","-jar","/taskOrganizer.jar"]

ARG APP_NAME=TaskOrganizer
ARG APP_VERSION=0.0.1